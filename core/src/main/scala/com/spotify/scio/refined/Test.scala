package com.spotify.scio.refined

import com.spotify.scio.bigquery.types.BigQueryTag
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.{AllOf, And, Not, Or}
import eu.timepit.refined.char.{Digit, LetterOrDigit}
import eu.timepit.refined.collection.{MaxSize, NonEmpty, Tail}
import eu.timepit.refined.generic.Equal
import eu.timepit.refined.numeric.{Greater, Less, Positive}
import eu.timepit.refined.string.{MatchesRegex, StartsWith}
import eu.timepit.refined.auto._
import shapeless.{::, HNil}

import scala.reflect.runtime.universe._

object Test {

  type UserName = String Refined NonEmpty

  type StrictName = String Refined And[NonEmpty, StartsWith[W.`"S"`.T]]
  type AStrName = String Refined StrictName
  case class Row(user: UserName, age: Int)

  type IT = Int Refined MaxSize[W.`35`.T]
  type CT = Char Refined Digit
  type LT = Long Refined MaxSize[W.`35`.T]
  type DT = Double Refined MaxSize[W.`35.5`.T]
  type FT = Float Refined MaxSize[W.`35.5`.T]
  type BDT = BigDecimal Refined MaxSize[W.`20000`.T]

//  type Handle = String Refined AllOf[StartsWith[W.`"S"`.T] :: MaxSize[W.`16`.T] :: HNil]
  type TwitterHandle = String Refined AllOf[
    StartsWith[W.`"@"`.T] ::
      MaxSize[W.`16`.T] ::
      Not[MatchesRegex[W.`"(?i:.*twitter.*)"`.T]] ::
      Not[MatchesRegex[W.`"(?i:.*admin.*)"`.T]] ::
      Tail[Or[LetterOrDigit, Equal[W.`'_'`.T]]] ::
      HNil
  ]
  type Name = String Refined And[NonEmpty, MaxSize[W.`256`.T]]

  type PInt = Int Refined Positive
  type ZeroToOne = Not[Less[W.`0.0`.T]] And Not[Greater[W.`1.0`.T]]

  def main(args: Array[String]): Unit = {
    val tt = typeTag[UserName]
    val stt = typeTag[StrictName]
    val astt = typeTag[AStrName]
    val th = typeTag[TwitterHandle]
    val pit = typeTag[PInt]
    val ztoO = typeTag[ZeroToOne]
    val it = typeTag[IT]
    val ct = typeTag[CT]
    val lt = typeTag[LT]
    val dt = typeTag[DT]
    val ft = typeTag[FT]
    val bdt = typeTag[BDT]
    val ttt = tq"${tt.tpe} @${typeOf[BigQueryTag]}"

    // println(ttt.isInstanceOf[AnnotatedType])
    // println(ttt.toString())
  }

}
