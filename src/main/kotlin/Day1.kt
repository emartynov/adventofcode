import java.io.File

fun calculate(number: Int, delta: Int) = number + delta

fun convert(value: String) = value.toInt()

fun readNumbersFromFile(file: File): List<Int> {
    return file.readLines()
        .map { convert(it) }
}

fun calculateDay1CollectSolution(file: File) = readNumbersFromFile(file)
    .fold(0, ::calculate)

fun calculateDay1FrequencySolution(file: File): Int {
    val numbers = readNumbersFromFile(file)
    val set = mutableSetOf<Int>()

    var frequency = 0
    var result = Int.MIN_VALUE
    while (result == Int.MIN_VALUE) {
        for (delta in numbers) {
            frequency = calculate(frequency, delta)

            if (set.contains(frequency)) {
                result = frequency
                break
            } else {
                set.add(frequency)
            }
        }
    }

    return result
}