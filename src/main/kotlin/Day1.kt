import java.io.File

fun calculate(number: Int, delta: Int) = number + delta

fun convert(value: String) = value.toInt()

fun readNumbersFromFile(file: File) = file.readLines()

fun calculateDay1CollectSolution(file: File) = readNumbersFromFile(file)
    .map { convert(it) }
    .fold(0, ::calculate)

fun calculateDay1FrequencySolution(file: File): Int {
    val set = mutableSetOf<Int>()

    val numbers = readNumbersFromFile(file)
        .map { convert(it) }

    var start = 0
    while (true) {
        start = numbers.fold(start) { acc, new ->
            val temp = calculate(acc, new)

            if (set.contains(temp)) {
                return temp
            } else
                set.add(temp)

            temp
        }
    }
}