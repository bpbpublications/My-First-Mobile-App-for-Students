class Person(
    val name: String,
    val job: String,
    val salary: Int,
)

fun main(){
    val o1 = Person("Raj", "App Developer", 90000)
    val o2 = Person("Sam", "Website Developer", 25000)
    println(o1.name)
    println(o1.job)
    println(o1.salary)
}