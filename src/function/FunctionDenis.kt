package function

import kotlin.math.pow

class FunctionDenis(
    start: Double,
    end: Double,
    tableSize: Int
) : AbstractFunction(start, end, tableSize) {

    override fun value(x: Double): Double {
        return 5.5 * ((4 + x * x).pow(1.0 / 3.0))
    }

    override fun toString(): String {
        return "f(x) = sin(x)"
    }
}