package com.spotify.scio.refined

import com.google.api.services.bigquery.model.TableFieldSchema
import com.spotify.scio.bigquery.validation.OverrideTypeProvider
import eu.timepit.refined.api.Refined

import scala.reflect.macros.blackbox
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

class RefinedTypeProvider extends OverrideTypeProvider {

  override def shouldOverrideType(tfs: TableFieldSchema): Boolean =
    throw new RuntimeException(s"Should override called with $tfs")
  //    false

  override def shouldOverrideType(c: blackbox.Context)(tpe: c.Type): Boolean = {
    //    throw new RuntimeException(s"Should override called with $tpe")
    val res = tpe.baseClasses.exists(p => p.fullName == "eu.timepit.refined.api.Refined")
    println(s"Results for the type $tpe is $res")
    res
  }

  override def shouldOverrideType(tpe: universe.Type): Boolean = {
    println("RefinedTypeProvider Called")
    //    val tq"$atype @$_" = tpe
    //    atype.asInstanceOf[Type].baseClasses.exists(p => p.fullName == "eu.timepit.refined.api.Refined")

    throw new RuntimeException(s"Should override called with ${tpe.toString}")
  }

  override def getScalaType(c: blackbox.Context)(tfs: TableFieldSchema): c.Tree = ???

  override def createInstance(c: blackbox.Context)(tpe: c.Type, tree: c.Tree): c.Tree = {
    import c.universe._
    q"""
     import _root_.eu.timepit.refined.auto._
      val x: $tpe = ${tree.toString}
      x
     """
  }

  override def initializeToTable(c: blackbox.Context)(
    modifiers: c.universe.Modifiers,
    variableName: c.universe.TermName,
    tpe: c.universe.Tree
  ): Unit = ()

  override def getBigQueryType(tpe: universe.Type): String =
    tpe.dealias.typeArgs.headOption
      .map {
        case t if t =:= typeOf[Boolean]    => "BOOLEAN"
        case t if t =:= typeOf[Int]        => "INTEGER"
        case t if t =:= typeOf[Long]       => "INTEGER"
        case t if t =:= typeOf[Float]      => "FLOAT"
        case t if t =:= typeOf[Double]     => "FLOAT"
        case t if t =:= typeOf[BigDecimal] => "NUMERIC"
        case _                             => throw new RuntimeException(s"Unsupported refined type: $tpe")
      }
      .getOrElse(
        throw new RuntimeException(
          s"Couldn't get the typeArts from $tpe, Please report this as a bug"
        )
      )

  def refinedTypeProvider[T: TypeTag]: Any = {
    val t = typeOf[T]
    println(s"Calling the refined converter")
    println(showRaw(t))
    //    import eu.timepit.refined.auto._
    null
  }
}
