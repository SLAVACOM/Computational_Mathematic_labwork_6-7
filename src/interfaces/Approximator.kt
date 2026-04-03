package interfaces

import function.FunctionValue

interface Approximator {
    fun leastSquares(table: Array<FunctionValue>, degree: Int = table.size): DoubleArray
}