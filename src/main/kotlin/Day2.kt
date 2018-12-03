import java.io.File

enum class WordType {
    NONE,
    DOUBLE,
    TRIPLE,
    DOUBLE_AND_TRIPLE
}

fun calculateRepeatedChars(s: String): WordType {
    val value = s.groupBy { it }
        .map { it.value.size }
        .filter { it == 2 || it == 3 }
        .distinct()
        .sum()

    return when (value) {
        0 -> WordType.NONE
        2 -> WordType.DOUBLE
        3 -> WordType.TRIPLE
        5 -> WordType.DOUBLE_AND_TRIPLE
        else -> throw IllegalStateException("Something wrong")
    }
}


fun checksum(l: List<String>): Int {
    val worldsByType = l.map { calculateRepeatedChars(it) }
        .groupBy { it }
        .mapValues { it -> it.value.size }

    val doubleAndTripleWords = worldsByType[WordType.DOUBLE_AND_TRIPLE] ?: 0
    val doubleWords = worldsByType[WordType.DOUBLE] ?: 0
    val tripleWords = worldsByType[WordType.TRIPLE] ?: 0

    return (doubleWords + doubleAndTripleWords) * (tripleWords + doubleAndTripleWords)
}

fun calculateFileChecksum(file: File) = checksum(file.readLines())
