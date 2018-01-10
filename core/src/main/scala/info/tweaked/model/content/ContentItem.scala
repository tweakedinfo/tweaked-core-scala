package info.tweaked.model.content

trait ContentItemBody

case class ContentItem(
  details:ContentItemDetails = ContentItemDetails(),
  body:ContentItemBody
)

case class ContentItemDetails(
  title:Option[String] = None,
  length:Option[String] = None,
  category: ContentItemCategory = ContentItemCategory.Other,
  description:Option[String] = None
)

case object Placeholder extends ContentItemBody
case class AlternativeBody(bodies:Seq[ContentItemBody]) extends ContentItemBody


trait ContentItemCategory
object ContentItemCategory {
  case object Message extends ContentItemCategory

  case object Web extends ContentItemCategory
  case object RecommendedReading extends ContentItemCategory

  case object Video extends ContentItemCategory

  case object Tutorial extends ContentItemCategory
  case object ProgrammingTutorial extends ContentItemCategory
  case object ActiveTutorial extends ContentItemCategory
  case object Exercise extends ContentItemCategory
  case object GitHub extends ContentItemCategory

  case object Discussion extends ContentItemCategory
  case object Slack extends ContentItemCategory

  case object Quiz extends ContentItemCategory

  case object Other extends ContentItemCategory

}


