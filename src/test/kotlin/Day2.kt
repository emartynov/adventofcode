import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day2Should {
    @Test
    fun `Returns NONE when no double chars is present`() {
        assertThat(calculateRepeatedChars("abcdes")).isEqualTo(WordType.NONE)
    }

    @Test
    fun `Return DOUBLE when double chars are present`() {
        assertThat(calculateRepeatedChars("aabcdes")).isEqualTo(WordType.DOUBLE)
    }

    @Test
    fun `Return TRIPPLE when triple chars are present`() {
        assertThat(calculateRepeatedChars("abcdbesb")).isEqualTo(WordType.TRIPLE)
    }

    @Test
    fun `Returns DOUBLE_AND_TRIPLE when triple and double chars are present`() {
        assertThat(calculateRepeatedChars("abcdbcesb")).isEqualTo(WordType.DOUBLE_AND_TRIPLE)
    }

    @Test
    fun `Calculate 0 as checksum of words with two chars and no triple words`() {
        val numberOfDoubles = checksum(listOf("aa", "bb", "c"))

        assertThat(numberOfDoubles).isEqualTo(0)
    }

    @Test
    fun `Calculate 0 as checksum of words with three chars and no doubles words`() {
        val numberOfDoubles = checksum(listOf("aaa", "bbb", "c"))

        assertThat(numberOfDoubles).isEqualTo(0)
    }

    @Test
    fun `Calculate 2 as checksum of two words with double chars and one word with triple`() {
        val numberOfDoubles = checksum(listOf("aa", "bb", "ccc"))

        assertThat(numberOfDoubles).isEqualTo(2)
    }

    @Test
    fun `Calculate 2 as checksum of two words with triple chars and one word with double`() {
        val numberOfDoubles = checksum(listOf("aaa", "bbb", "cc"))

        assertThat(numberOfDoubles).isEqualTo(2)
    }

    @Test
    fun `Calculate 1 as checksum of one word that has triple and double chars`() {
        val numberOfDoubles = checksum(listOf("aaabb"))

        assertThat(numberOfDoubles).isEqualTo(1)
    }

    @Test
    fun `Calculate checksum from file`() {
        val tempFile = createTempFile()
        tempFile.writeText(
            """aaa
            |bb""".trimMargin()
        )

        val checksum = calculateFileChecksum(tempFile)

        assertThat(checksum).isEqualTo(1)
    }

    @Test
    fun `Transform word to map`() {
        assertThat("abca".transformToCharsMap()).isEqualTo(mapOf('a' to 2, 'b' to 1, 'c' to 1))
    }

    @Test
    fun `Number of different characters`() {
        val word1 = "abcd"
        val word2 = "abce"

        assertThat(
            numberOfDifferentCharactersInWords(
                word1.transformToCharsMap(),
                word2.transformToCharsMap()
            )
        ).isEqualTo(1)
    }

    @Test
    fun `Return correct pair that has only one char difference`() {
        val words = listOf("aaa", "ccc", "bbb", "aab")

        assertThat(oneCharacterPair(words)).isEqualTo(Pair("aaa", "aab"))
    }

    @Test
    fun `Return non changed chars`() {
        val words = Pair("aaa", "aab")

        assertThat(commonChars(words)).isEqualTo("aa")
    }
}