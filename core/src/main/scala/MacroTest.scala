
object Welcome extends App {

    @Benchmark
    def testMethod[String]: Double = {
        val x = 2.0 + 2.0
        Math.pow(x,x)
    }


    println(testMethod)
}