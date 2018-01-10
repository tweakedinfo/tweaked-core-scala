package info.tweaked.model.content

case class YouTubeVideo(id:String) extends ContentItemBody

case class KalturaVideo(wId:String, uiConfId:String, id:String) extends ContentItemBody

case object Echo360Video extends ContentItemBody

case class SlidesFile(path:String) extends ContentItemBody