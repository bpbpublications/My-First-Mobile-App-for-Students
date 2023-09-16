// 1. defining a simple function with return type
fun findSpeed(distance: Int, time: Int): Int {
    return distance / time
}

fun main() {
    // 2. calling a function that returns the answer
    val speed = findSpeed(100, 5)
    println("the calculated speed is $speed")
}