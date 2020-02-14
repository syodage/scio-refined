import scala.annotation.StaticAnnotation
import scala.reflect.macros.whitebox.Context

class Benchmark extends StaticAnnotation {

  def macroTranform(annottees: Any*) = macro Benchmark.impl
}

object Benchmark {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val result = annottees.map(_.tree).toList match {
      case q"$mods def $methodName[..$tpes](...$args): $returnType = { ...$body }" :: Nil => {
        q""" $mods def $methodName[..$tpes](...$args): $returnType = {
                val start = System.nanoTime()
                val result = {..$body}
                val end  = System.nanoTime()
                println(${methodName.toString} + " elapsed time: " + (end - start) + "ns")
                result
        """
      }

    }
  }
}
