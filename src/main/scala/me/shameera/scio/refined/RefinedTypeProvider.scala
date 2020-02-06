package me.shameera.scio.refined

import com.google.api.services.bigquery.model.TableFieldSchema
import com.spotify.scio.bigquery.validation.OverrideTypeProvider

import scala.reflect.macros.blackbox
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

class RefinedTypeProvider extends  OverrideTypeProvider {
  override def shouldOverrideType(tfs: TableFieldSchema): Boolean = ???

  override def shouldOverrideType(c: blackbox.Context)(tpe: c.Type): Boolean = ???

  override def shouldOverrideType(tpe: universe.Type): Boolean =
    tpe.baseClasses.exists(p => p.fullName == "eu.timepit.refined.api.Refined")

  override def getScalaType(c: blackbox.Context)(tfs: TableFieldSchema): c.Tree = ???

  override def createInstance(c: blackbox.Context)(tpe: c.Type, tree: c.Tree): c.Tree = ???

  override def initializeToTable(c: blackbox.Context)(
    modifiers: c.universe.Modifiers,
    variableName: c.universe.TermName,
    tpe: c.universe.Tree
  ): Unit = ()

  override def getBigQueryType(tpe: universe.Type): String = {
    tpe.dealias.typeArgs.headOption.map{
      case t if t =:= typeOf[Boolean] => "BOOLEAN"
      case t if t =:= typeOf[Int] => "INTEGER"
      case t if t =:= typeOf[Long] => "INTEGER"
      case t if t =:= typeOf[Float] => "FLOAT"
      case t if t =:= typeOf[Double] => "FLOAT"
      case t if t =:= typeOf[BigDecimal] => "NUMERIC"
      case _ => throw new RuntimeException(s"Unsupported refined type: $tpe")
    }.getOrElse(throw new RuntimeException(
      s"Couldn't get the typeArts from $tpe, Please report this as a bug"))
  }
}
