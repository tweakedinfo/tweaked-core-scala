package info.tweaked.model.unit

import info.tweaked.model.content.{ContentItem, TopicSection}
import info.tweaked.model.plan.Prerequisite
import info.tweaked.model.plan.{Prerequisite, Requires}
import units.CBOK

import scala.util.Random

/**
  * A teaching unit, such as might exist in a regular degree
  */
case class TeachingUnit(
  code:String, name:Option[String]=None, fuzz:Option[String] = None,

  description:Seq[String] = Seq.empty,

  details: TeachingUnitDetails = TeachingUnitDetails(),
  outcomes:Seq[String] = Seq.empty,

  taught:Seq[AssessmentDescription] = Seq.empty,
  assessment:Seq[AssessmentDescription] = Seq.empty,

  prerequisite:Prerequisite = Prerequisite.Yes, restricted:Prerequisite = Prerequisite.No, cp:Int = 6,
  readyQuiz:Option[ReadyQuiz] = None,

  resources:Resources = Resources(),

  preview:Option[Preview] = None

)

case class TeachingUnitDetails(
  welcome:Option[ContentItem] = None,
  backgroundImg:Option[String] = None,
  tileImg:Option[String] = None
)

case class Preview(topics:Seq[TopicSection])

case class LODescription(text:String, courseLOs:Seq[Int])

case class AssessmentDescription(title:String, description:Option[String] = None, percentage:Int = 0, lo:Seq[Int] = Seq.empty, cbok:Seq[CBOK.At] = Seq.empty)

object TeachingUnit {
  implicit def prereq(u:TeachingUnit):Prerequisite = Prerequisite.Contains(u)

  /** An unlisted unit */
  def x(s:String, t:Option[String] = None) = TeachingUnit(s, name=t, fuzz = Some(Random.nextString(8)))

  def old(c:String, s:String) = TeachingUnit(c, name=Some(s))

  val Unchosen = x("Unchosen")

  val any = x("Any")
  def elective = x("Elective")
  val Listed = x("Listed")
  val Prescribed = x("Prescribed")

}

case class NamedSet(name:String, units:Seq[TeachingUnit],  notes:Seq[String] = Seq.empty)

case class ReadyQuiz(questions:Seq[RQMCQuestion])

case class RQMCQuestion(text:String,options:Seq[RQMCOption])

case class RQMCOption(text:String, response:String)

object Resources {

  sealed trait Interactivity
  case object Forum extends Interactivity
  case object GitHub extends Interactivity
  case object Slack extends Interactivity
  case object FacebookPage extends Interactivity
  case object VideoRecordings extends Interactivity
  case object ProgrammingEnvironments extends Interactivity
  case object Quizzes extends Interactivity
  case object GroupWork extends Interactivity

  trait RRR
  case object Required extends RRR
  case object Recommended extends RRR
  case object Referenced extends RRR

  case class Textbook(
     rrr:RRR,
     title:String,
     authors:String,
     isbn13:Option[String] = None,
     edition:Option[String] = None,
     publisher:Option[String] = None
   )

}

case class Resources(
  interactivity:Seq[Resources.Interactivity] = Seq.empty,
  textbooks:Seq[Resources.Textbook] = Seq.empty
)