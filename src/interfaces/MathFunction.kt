package interfaces

import kotlin.math.abs

abstract class MathFunction {
    abstract fun value(x: Double): Double

    protected fun gaussSolve(A: Array<DoubleArray>, B: DoubleArray): DoubleArray {
        val n = B.size
        val a = B.copyOf()
        val M = Array(n) { i -> A[i].copyOf() }

        for (k in 0..<n) {
            // Находим ведущий элемент
            var max = abs(M[k][k])
            var maxRow = k
            for (i in k + 1..<n) {
                if (abs(M[i][k]) > max) {
                    max = abs(M[i][k])
                    maxRow = i
                }
            }

            // Меняем строки
            val tmp = M[k]; M[k] = M[maxRow]; M[maxRow] = tmp
            val tmpB = a[k]; a[k] = a[maxRow]; a[maxRow] = tmpB

            // Прямой ход
            for (i in k + 1..<n) {
                val factor = M[i][k] / M[k][k]
                for (j in k..<n) {
                    M[i][j] -= factor * M[k][j]
                }
                a[i] -= factor * a[k]
            }
        }

        // Обратный ход
        val x = DoubleArray(n)
        for (i in n - 1 downTo 0) {
            var sum = 0.0
            for (j in i + 1..<n) {
                sum += M[i][j] * x[j]
            }
            x[i] = (a[i] - sum) / M[i][i]
        }
        return x
    }
}