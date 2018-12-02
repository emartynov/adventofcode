import java.io.File

class Day1

fun main() {
    val url = Day1::class.java.getResource("input1.txt")
    val inputFile = File(url.path)

    println(calculateDay1CollectSolution(inputFile))
    println(calculateDay1FrequencySolution(inputFile))
}