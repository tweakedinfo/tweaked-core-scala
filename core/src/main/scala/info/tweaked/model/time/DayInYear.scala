package info.tweaked.model.time

import scala.languageFeature.implicitConversions

/**
  * A day in a year.
  *
  * Scala has different date classes in the JVM versus JS. This holds a minimal amount of information in a form that
  * converts easily to a Unix epoch.
  */
case class DayInYear(year:Int, yday:Int) {

  /**
    * Converts to a "seconds since 1 Jan 1970, ignoring leap-seconds" Unix epoch
    * @return
    */
  def toEpochSecs(hr:Int=0, min:Int=0, sec:Int=0) = {
    sec + min * 60 + hr * 3600 + yday * 86400 + (
      (year - 70) * 31536000L + ((year - 69) / 4) * 86400L -
        ((year - 1) / 100) * 86400L + ((year + 299)/400) * 86400L
      )
  }

  /**
    * Converts to a "milliseconds since 1 Jan 1970, ignoring leap-seconds" Unix epoch, as used in JavaScript dates
    * @return
    */
  def toEpochMs(hr:Int=0, min:Int=0, sec:Int=0):Long = toEpochSecs(hr, min, sec) * 1000L

  def toEpochDays = toEpochSecs() / 86400

  def dayOfWeek = {
    // Jan 1 1970 (0 epoch) was a Thursday
    ((toEpochDays + 4) % 7).toInt
  }

  def nearest(day:Int) = {
    val dow = dayOfWeek
    if (dow <= day) {
      DayInYear(year, dow + (day - dow))
    } else {
      DayInYear(year, dow + (7 + day - dow))
    }
  }



}

object DayInYear {

  implicit class DayConv(val d:Int) extends AnyVal {
    def jan(y:Int):DayInYear = new Month(0).apply(d)(y)
    def feb(y:Int):DayInYear = new Month(1).apply(d)(y)
    def mar(y:Int):DayInYear = new Month(2).apply(d)(y)
    def apr(y:Int):DayInYear = new Month(3).apply(d)(y)
    def may(y:Int):DayInYear = new Month(4).apply(d)(y)
    def jun(y:Int):DayInYear = new Month(5).apply(d)(y)
    def jul(y:Int):DayInYear = new Month(6).apply(d)(y)
    def aug(y:Int):DayInYear = new Month(7).apply(d)(y)
    def sep(y:Int):DayInYear = new Month(8).apply(d)(y)
    def oct(y:Int):DayInYear = new Month(9).apply(d)(y)
    def nov(y:Int):DayInYear = new Month(10).apply(d)(y)
    def dec(y:Int):DayInYear = new Month(11).apply(d)(y)
  }

  implicit def toEpoch(d:DayInYear):Long = d.toEpochMs()

}



class Month(i:Int) {

  def apply(day:Int) = (year:Int) => {
    val d = Month.daysInEachMonth(year).slice(0, i).sum + day
    DayInYear(year, d)
  }
}

object Month {

  def leapYear(year:Int):Boolean = (year % 4 == 0) && ((year % 100 != 0) || (year % 400) == 0)

  def daysInEachMonth(year:Int) = Seq(
    31,
    if (leapYear(year)) 29 else 28,
    31,
    30,
    31,
    30,
    31,
    31,
    30,
    31,
    30,
    31
  )

  val jan = new Month(0)
  val feb = new Month(1)
  val mar = new Month(2)
  val apr = new Month(3)
  val may = new Month(4)
  val jun = new Month(5)
  val jul = new Month(6)
  val aug = new Month(7)
  val sep = new Month(8)
  val oct = new Month(9)
  val nov = new Month(10)
  val dec = new Month(11)

}




