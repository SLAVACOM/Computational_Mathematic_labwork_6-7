package function

import kotlin.math.E
import kotlin.math.pow
import kotlin.math.sqrt

class Function(
    start: Double,
    end: Double,
    tableSize: Int
) : AbstractFunction(start, end, tableSize) {

    override fun value(x: Double): Double {
        return 1.5 * E.pow(sqrt(x.pow(2) + 4))
    }

    override fun toString(): String {
        return "f(x) = 1.5 * e^(sqrt(x^2 + 4))"
    }
}