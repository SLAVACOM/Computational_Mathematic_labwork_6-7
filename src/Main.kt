import function.Function
import function.FunctionDenis
import function.FunctionExample
import kotlin.math.PI

fun main() {
    val function = Function(0.0, 5.0, 3)

    println("Функция: $function")
    function.printTable()
    function.printComparison()
//
//    val functionDenis = FunctionDenis(0.0, 2.0, 3)
//    println("Функция: $functionDenis")
//    functionDenis.printTable()
//    functionDenis.printComparison()
//
//    val functionExample = FunctionExample(PI/6, PI/2, 3)
//    println("Функция: $functionExample")
//    functionExample.printTable()
//    functionExample.printComparison()
}