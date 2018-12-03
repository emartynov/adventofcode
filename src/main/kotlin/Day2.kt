import java.io.File
import kotlin.math.absoluteValue

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
        .mapValues { it.value.size }

    val doubleAndTripleWords = worldsByType[WordType.DOUBLE_AND_TRIPLE] ?: 0
    val doubleWords = worldsByType[WordType.DOUBLE] ?: 0
    val tripleWords = worldsByType[WordType.TRIPLE] ?: 0

    return (doubleWords + doubleAndTripleWords) * (tripleWords + doubleAndTripleWords)
}

fun calculateFileChecksum(file: File) = checksum(file.readLines())

fun String.transformToCharsMap() = this.groupBy { it }.mapValues { it.value.size }

fun numberOfDifferentCharactersInWords(mapOfWord1: Map<Char, Int>, mapOfWord2: Map<Char, Int>): Int {
    return mapOfWord1.map { entry -> (entry.value - (mapOfWord2[entry.key] ?: 0)).absoluteValue }
        .sum()
}

fun oneCharacterPair(list: List<String>): Pair<String, String> {
    val mapOfChars = list.map { it to it.transformToCharsMap() }.toMap()

    for (i in 0 until list.size) {
        for (j in i until list.size) {
            if (numberOfDifferentCharactersInWords(mapOfChars[list[i]]!!, mapOfChars[list[j]]!!) == 1)
                return Pair(list[i], list[j])
        }
    }

    return Pair("", "")
}

fun commonChars(words: Pair<String, String>): String {
    val firstWordLength = words.first.length
    if (firstWordLength != words.second.length) return ""

    val builder = StringBuilder(firstWordLength)

    for (i in 0 until firstWordLength) {
        if (words.first[i] == words.second[i])
            builder.append(words.first[i])
    }

    return builder.toString()
}

fun onePairNonChangedChars(file: File) = commonChars(oneCharacterPair(file.readLines()))
