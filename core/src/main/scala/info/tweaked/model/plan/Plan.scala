package info.tweaked.model.plan

import info.tweaked.model.unit.{Term, TeachingUnit, Offering}

/**
  * Created by wbilling on 30/10/2016.
  */
case class Plan(name:String, description:Seq[String] = Seq.empty, selection:Seq[(Term, Seq[TeachingUnit])] = Seq.empty) {

  lazy val allUnits:Seq[TeachingUnit] = selection.flatMap(_._2)

  def unitInPlan(u:TeachingUnit):Boolean = {
    selection.flatMap(_._2).exists(_.contains(u))
  }

  def beforeStartOf(t:Term):Seq[(Term, Seq[TeachingUnit])] = {
    selection.filter(_._1.beforeStartOf(t))
  }

}
