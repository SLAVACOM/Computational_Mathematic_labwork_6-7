package interfaces

import function.FunctionValue
import java.util.*
import kotlin.math.log10

interface Tabulator {

    val eps: Double
        get() = 1e-4

    fun table(): Array<FunctionValue>
    fun decimals(): Int = -log10(eps).toInt()
    fun colWidth(): Int = 4 + 1 + decimals()
    fun makeLine(left: String, mid: String, right: String, cols: Int): String =
        left + List(cols) { "─".repeat(colWidth()) }.joinToString(mid) + right

    fun printTable() {
        val table = table()
        val f = "%.${decimals()}f"

        val topLine = makeLine("┌", "┬", "┐", 2)
        val sepLine = makeLine("├", "┼", "┤", 2)
        val bottomLine = makeLine("└", "┴", "┘", 2)

        println("\nТаблица значений функции:")
        println(topLine)
        println("│" + listOf("x", "f(x)").joinToString("│") { it.padStart(colWidth()) } + "│")
        println(sepLine)

        table.forEach {
            println("│" + listOf(it.x, it.fx).joinToString("│") { v ->
                f.format(Locale.US, v).padStart(colWidth())
            } + "│")
        }

        println(bottomLine)
    }

    fun printComparison()
}