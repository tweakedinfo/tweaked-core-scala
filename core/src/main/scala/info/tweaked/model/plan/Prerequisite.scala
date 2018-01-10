package info.tweaked.model.plan

import info.tweaked.model.unit.{Term, TeachingUnit}


/**
  * A condition that is true or false, depending on the units the student has already taken
  */
trait Prerequisite {
  def apply(units: Seq[TeachingUnit]): Boolean

  def or (p:Prerequisite) = Prerequisite.Or(Seq(this, p))

  def and (p:Prerequisite) = Prerequisite.And(Seq(this, p))

  def stringify:String

  def units:Seq[TeachingUnit] = Seq.empty

  def contains(u:TeachingUnit):Boolean = false
}

object Prerequisite {

  /** A condition that is always true */
  case object Yes extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = true

    def stringify = "Yes"
  }

  /** A condition that is always false */
  case object No extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = false

    def stringify = "No"
  }

  /** A condition that is true if any of its children are */
  case class Or(prereqs:Seq[Prerequisite]) extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = prereqs.foldLeft(false)(_ || _.apply(units))

    def stringify = prereqs.map(_.stringify).mkString("(", " or ", ")")
  }

  /** A condition that is true if all of its children are */
  case class And(prereqs:Seq[Prerequisite]) extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = prereqs.forall(_.apply(units))

    override def units = prereqs.flatMap(_.units)

    def stringify = prereqs.map(_.stringify).mkString("(", " and ", ")")
  }

  /** A condition that the units so far contains a minimum number of credit points */
  case class minCP(cps:Int) extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = units.foldLeft(0)(_ + _.cp) >= cps

    def stringify = s"$cps credit points"
  }

  /** A condition that the units so far includes a set of units */
  case class Contains(required: TeachingUnit*) extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = required.diff(units).isEmpty

    override def contains(u:TeachingUnit) = required.contains(u)

    override def units = required

    def stringify = required.map(_.code).mkString("(", ", ", ")")
  }

  case class NumFrom(num:Int, required:TeachingUnit*) extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = required.intersect(units).size >= num

    override def contains(u:TeachingUnit) = required.contains(u)

    override def units = required

    def stringify = s"$num from ${required.map(_.code).mkString("(", ", ", ")")}"
  }

  case class NumSatisfying(num:Int, check: (TeachingUnit) => Boolean, condStr:String = "satisfying a condition") extends Prerequisite {
    def apply(units:Seq[TeachingUnit]) = units.count(check) >= num

    def stringify = s"$num units $condStr"
  }


}

case class NamedRule(name:String, rule:Prerequisite, extra:Seq[TeachingUnit] = Seq.empty, notes:Seq[String] = Seq.empty) {
  def contains(u:TeachingUnit) = rule.contains(u) || extra.contains(u)
}

case class Course(
  short:String, name:String, rules:Seq[NamedRule],
  coloringRule:(TeachingUnit) => String = _ => "",
  majors:Seq[Major] = Seq.empty, minMaj:Int = 0,
  standing: Standing = Standing.Empty) {
  def units = rules.flatMap(_.rule.units)
}

case class Major(name:String, rules:Seq[NamedRule])

trait Attendance
case object OnCampus extends Attendance
case object OffCampus extends Attendance

trait FTPT
case object FullTime extends FTPT
case object PartTime extends FTPT

case class Intake(course:Course, attendance: Attendance, ftpt:FTPT, start:Term)
