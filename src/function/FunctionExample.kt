package function

import kotlin.math.sin

class FunctionExample(
    start: Double,
    end: Double,
    tableSize: Int
) : AbstractFunction(start, end, tableSize) {

    override fun value(x: Double): Double {
        return sin(x)
    }

    override fun toString(): String {
        return "f(x) = sin(x)"
    }
}