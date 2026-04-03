import kotlin.math.exp
import kotlin.math.sqrt

val coef = arrayOf(5.542, -14.035, 12.689)
val xi = arrayOf(0, 1, 2)
val fi = arrayOf(11.084, 14.035, 25.378)

fun main() {

    var x = 0.0
    while (x <= 2.0) {
        println(String.format("%.12f %.12f %.12f %.12f %.12f", f(x), L2(x), N1(x), N2(x), p2(x)))
        x += 0.2
    }

    showSums()

}

fun f(x: Double): Double {
    return 1.5 * exp(sqrt(x * x + 4))
}

fun L2(x: Double): Double {
    val a1 = (coef[0] * (x - xi[1]) * (x - xi[2]))
    val a2 = (coef[1] * (x - xi[0]) * (x - xi[2]))
    val a3 = (coef[2] * (x - xi[0]) * (x - xi[1]))
    return a1 + a2 + a3
}

fun N1(x: Double): Double {
    val h = xi[1] - xi[0]
    val t = (x - xi[0]) / h

    return fi[0] +
            (fi[1] - fi[0]) * t +
            (t * (t - 1)) / 2 * (fi[2] - 2 * fi[1] + fi[0])
}

fun N2(x: Double): Double {
    val h = xi[1] - xi[0]
    val t = (x - xi[2]) / h

    return fi[2] +
            (fi[2] - fi[1]) * t +
            (t * (t + 1)) / 2 * (fi[2] - 2 * fi[1] + fi[0])
}

val sumX = xi.sum()
val sumX2 = xi.sumOf { it * it }
val sumX3 = xi.sumOf { it * it * it }
val sumX4 = xi.sumOf { it * it * it * it }

val sumFX = fi.sum()

val sumFX2 = fi.sumOf { it * xi[fi.indexOf(it)] }
val sumFX3 = fi.sumOf { it * xi[fi.indexOf(it)] * xi[fi.indexOf(it)] }


fun showSums() {
    println("Σx = $sumX")
    println("Σx^2 = $sumX2")
    println("Σx^3 = $sumX3")
    println("Σx^4 = $sumX4")

    println("Σf(x) = $sumFX")
    println("Σf(x)*x = $sumFX2")
    println("Σf(x)*x^2 = $sumFX3")
}

fun p2(x: Double): Double {
    return 1049.0 / 250.0 * x * x - 249.0 / 200.0 * x + 2771.0 / 250
}

