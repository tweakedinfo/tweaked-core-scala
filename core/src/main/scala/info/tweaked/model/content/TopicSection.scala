package info.tweaked.model.content

import info.tweaked.model.plan.{Provides, Requires}


case class TopicSection(
  title: String,

  tileImage: Option[String] = None,
  wideImage: Option[String] = None,

  content: Seq[ContentItem] = Seq.empty,

  provides: Seq[Provides] = Seq.empty,
  requires: Seq[Requires] = Seq.empty


)
