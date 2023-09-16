class Cat(val breed: String, val age: Int) {

}

fun main() {
    val snowbell = Cat("Persian", 5)
    val tom = Cat("Siamese", 13)
    println(snowbell.breed)
    println(snowbell.age)
    println("--------")
    println(tom.breed)
    println(tom.age)
}