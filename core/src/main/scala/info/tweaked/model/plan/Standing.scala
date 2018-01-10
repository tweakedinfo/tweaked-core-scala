package info.tweaked.model.plan

import info.tweaked.model.unit.TeachingUnit

trait Standing {
  def apply(units:TeachingUnit*):Seq[TeachingUnit]
}

object Standing {

  case object Empty extends Standing {
    def apply(units:TeachingUnit*) = Seq.empty
  }

  case class All(standing:Standing*) extends Standing {
    def apply(units:TeachingUnit*) = standing.flatMap(_.apply(units:_*))
  }

  case class Filter(condition:(TeachingUnit) => Boolean) extends Standing {
    def apply(units:TeachingUnit*) = units.filter(condition)
  }

  case class FilterReplace(condition:(TeachingUnit) => Boolean, replace:(TeachingUnit) => TeachingUnit) extends Standing {
    def apply(units:TeachingUnit*) = for { u <- units } yield if(condition(u)) u else replace(u)
  }

  case class Grant(rule:Prerequisite, give:TeachingUnit*) extends Standing {
    def apply(units:TeachingUnit*) = if (rule.apply(units)) give else Seq.empty
  }

  case class First(rules:Standing*) extends Standing {
    def apply(units:TeachingUnit*) = rules.foldLeft(Seq.empty[TeachingUnit]) { case (s, r) =>
      if (s.isEmpty) r.apply(units:_*) else s
    }
  }

}
