package info.tweaked.model.unit

import info.tweaked.model.time.DayInYear
import DayInYear._

import scala.language.implicitConversions

/**
 * Universities have teaching periods, that run in a cycle
 */
case class Term(name:String, shortName:String, start:DayInYear, end:DayInYear) {
  def beforeStartOf(t:Term):Boolean = {
    this.end.toEpochDays < t.start.toEpochDays
  }
}

object Term {
  implicit def toSeq(t:Term):Seq[Term] = Seq(t)

  val t1_2017 = Term(
    "Trimester 1, 2017", "T1 2017", 20.feb(2017), 2.jun(2017)
  )
  val t2_2017 = Term(
    "Trimester 2, 2017", "T2 2017", 26.jun(2017), 9.oct(2017)
  )
  val t3_2017 = Term(
    "Trimester 3, 2017", "T3 2017", 23.oct(2017), 5.feb(2018)
  )
  val t1_2018 = Term(
    "Trimester 1, 2018", "T1 2018", 20.feb(2018), 2.jun(2018)
  )
  val t2_2018 = Term(
    "Trimester 2, 2018", "T2 2018", 26.jun(2018), 9.oct(2018)
  )
  val t3_2018 = Term(
    "Trimester 3, 2018", "T3 2018", 23.oct(2018), 5.feb(2019)
  )
  val t1_2019 = Term(
    "Trimester 1, 2019", "T1 2019", 20.feb(2019), 2.jun(2019)
  )
  val t2_2019 = Term(
    "Trimester 2, 2019", "T2 2019", 26.jun(2019), 9.oct(2019)
  )
  val yl1_2019 = Term(
    "Year-long 1, 2019", "YL1 2019", 20.feb(2019), 9.oct(2019)
  )
  val t3_2019 = Term(
    "Trimester 3, 2019", "T3 2019", 23.oct(2019), 5.feb(2020)
  )
  val t1_2020 = Term(
    "Trimester 1, 2020", "T1 2020", 20.feb(2020), 2.jun(2020)
  )
  val t2_2020 = Term(
    "Trimester 2, 2020", "T2 2020", 26.jun(2020), 9.oct(2020)
  )
  val t3_2020 = Term(
    "Trimester 3, 2020", "T3 2020", 23.oct(2020), 5.feb(2021)
  )
  val t1_2021 = Term(
    "Trimester 1, 2021", "T1 2021", 20.feb(2021), 2.jun(2021)
  )
  val t2_2021 = Term(
    "Trimester 2, 2021", "T2 2021", 26.jun(2021), 9.oct(2021)
  )
  val t3_2021 = Term(
    "Trimester 3, 2021", "T3 2021", 23.oct(2021), 5.feb(2022)
  )


  val terms = Seq(
    t1_2017, t2_2017, t3_2017,
    t1_2018, t2_2018, t3_2018,
    t1_2019, t2_2019, t3_2019,
    t1_2020, t2_2020, t3_2020,
    t1_2021, t2_2021, t3_2021
  )

}

case class Offering(unit:TeachingUnit, term:Term)