// 1. defining a simple function

fun area(height:Int, width:Int){
    val result = height * width
    print("area = $result") // string interpolation
}

fun main() {
    // 2. calling a function
    area(height = 5, width = 10)
}