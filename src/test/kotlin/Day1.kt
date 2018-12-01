import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day1Should {
    @Test
    fun `Apply delta to number`() {
        val num = 10
        val delta = -5

        val result = calculate(num, delta)

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `Convert string to integer`() {
        assertThat(convert("-10")).isEqualTo(-10)
        assertThat(convert("+10")).isEqualTo(10)
    }

    @Test
    fun `Read numbers from file`() {
        val tempFile = createTempFile()
        tempFile.writeText("-10\n+5\n")

        val sequence = readNumbersFromFile(tempFile)

        assertThat(sequence).isEqualTo(listOf("-10", "+5"))
    }

    @Test
    fun `Return solution for Day1`() {
        val tempFile = createTempFile()
        tempFile.writeText("-10\n+5\n")

        val result = calculateDay1CollectSolution(tempFile)

        assertThat(result).isEqualTo(-5)
    }

    @Test
    fun `Repeated frequency`() {
        val tempFile = createTempFile()
        tempFile.writeText("+3\n+3\n+4\n-2\n-4")

        val result = calculateDay1FrequencySolution(tempFile)

        assertThat(result).isEqualTo(10)
    }
}