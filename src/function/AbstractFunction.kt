package function

import interfaces.Approximator
import interfaces.Interpolator
import interfaces.MathFunction
import interfaces.Tabulator
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong

abstract class AbstractFunction(
    private val start: Double,
    private val end: Double,
    private val tableSize: Int
) : MathFunction(), Tabulator, Interpolator, Approximator {

    override fun table(): Array<FunctionValue> {
        val h = (end - start) / (tableSize - 1)
        return Array(tableSize) { i ->
            val x = start + i * h
            val fx = value(x)
            val roundedX = (x / eps).roundToLong() * eps
            val roundedFx = (fx / eps).roundToLong() * eps

            FunctionValue(roundedX, roundedFx)
        }
    }

    override fun lagrange(x: Double, table: Array<FunctionValue>): Double {
        var result = 0.0
        val coeffs = lagrangeCoefficients(table)
        for (i in table.indices) {
            var term = coeffs[i]
            for (j in table.indices) {
                if (i != j) {
                    term *= (x - table[j].x)
                }
            }
            result += term
        }
        return result
    }

    override fun newtonFirst(x: Double, table: Array<FunctionValue>): Double {
        val diff = finiteDifferences(table)
        val h = table[1].x - table[0].x
        val t = (x - table[0].x) / h

        var result = diff[0][0]
        var term = 1.0

        for (i in 1..<table.size) {
            term *= (t - (i - 1)) / i
            result += term * diff[0][i]
        }

        return result
    }

    override fun newtonSecond(x: Double, table: Array<FunctionValue>): Double {
        val diff = finiteDifferences(table)
        val h = table[1].x - table[0].x
        val t = (x - table.last().x) / h

        var result = diff.last()[0]
        var term = 1.0

        for (i in 1..<table.size) {
            term *= (t + (i - 1)) / i
            result += term * diff[table.size - i - 1][i]
        }

        return result
    }

    override fun leastSquares(table: Array<FunctionValue>, degree: Int): DoubleArray {
        val m = degree
        val A = Array(m) { DoubleArray(m) }
        val B = DoubleArray(m)

        val xPowers = DoubleArray(2 * m - 1) { k -> table.sumOf { it.x.pow(k) } }

        for (i in 0..<m) {
            for (j in 0..<m) {
                A[i][j] = xPowers[i + j]
            }
            val rawB = table.sumOf { it.fx * it.x.pow(i) }
            B[i] = (rawB / eps).roundToLong() * eps
        }
        return gaussSolve(A, B)
    }

    private fun lagrangeCoefficients(table: Array<FunctionValue>): DoubleArray {
        val coeffs = DoubleArray(table.size) { table[it].fx }

        for (i in table.indices) {
            for (j in table.indices) {
                if (i != j) {
                    coeffs[i] /= (table[i].x - table[j].x)
                }
            }
        }
        return coeffs
    }

    private fun finiteDifferences(table: Array<FunctionValue>): Array<Array<Double>> {
        val n = table.size
        val diff = Array(n) { Array(n) { 0.0 } }

        for (i in 0..<n) {
            diff[i][0] = table[i].fx
        }

        for (j in 1..<n) {
            for (i in 0..<n - j) {
                diff[i][j] = diff[i + 1][j - 1] - diff[i][j - 1]
            }
        }

        return diff
    }

    override fun printComparison() {
        val table = table()

        val coeffs = leastSquares(table)
        val f = "%.${decimals()}f"

        val headers = listOf("x", "f(x)", "L(x)", "|f-L|", "N1", "|f-N1|", "N2", "|f-N2|", "P2", "|f-P2|")
        val topLine = makeLine("┌", "┬", "┐", headers.size)
        val sepLine = makeLine("├", "┼", "┤", headers.size)
        val bottomLine = makeLine("└", "┴", "┘", headers.size)

        println("\nСравнительная таблица:")
        println(topLine)
        println("│" + headers.joinToString("│") { it.padStart(colWidth()) } + "│")
        println(sepLine)

        val h = (table.last().x - table.first().x) / 10
        var x = table.first().x

        while (x <= end) {
            val fx = value(x)
            val L = lagrange(x, table)
            val N1 = newtonFirst(x, table)
            val N2 = newtonSecond(x, table)
            val P = coeffs[0] + coeffs[1] * x + coeffs[2] * x * x

            val row = listOf(
                x, fx, L, abs(fx - L),
                N1, abs(fx - N1),
                N2, abs(fx - N2),
                P, abs(fx - P)
            )

            println("│" + row.joinToString("│") { f.format(Locale.US, it).padStart(colWidth()) } + "│")
            x += h
        }

        println(bottomLine)
    }
}

