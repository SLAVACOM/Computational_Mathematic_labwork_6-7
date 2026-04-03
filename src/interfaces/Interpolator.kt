package interfaces

import function.FunctionValue

interface Interpolator {
    fun lagrange(x: Double, table: Array<FunctionValue>): Double
    fun newtonFirst(x: Double, table: Array<FunctionValue>): Double
    fun newtonSecond(x: Double, table: Array<FunctionValue>): Double
}