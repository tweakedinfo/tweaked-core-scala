package info.tweaked.model.plan

import info.tweaked.model.unit.Offering

trait Problem

object Problem {

  case class PrerequisiteNotMet(unit: Offering) extends Problem
  case class Restricted(unit: Offering) extends Problem
  case class AlreadyDone(unit: Offering) extends Problem

}