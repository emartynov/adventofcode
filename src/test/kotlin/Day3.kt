import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day3Should {
    @Test
    fun `Parse claim from string`() {
        assertThat("#1 @ 1,3: 4x4".toClaim()).isEqualTo(Claim(id = 1, x = 1, y = 3, width = 4, height = 4))
    }

    @Test
    fun `Calculate map of claims`() {
        assertThat(buildMap(listOf(Claim(1, 0, 0, 2, 2))))
            .isEqualTo(mapOf(Pair(0, 0) to 1, Pair(0, 1) to 1, Pair(1, 0) to 1, Pair(1, 1) to 1))
    }

    @Test
    fun `Calculate claimed more then one time`() {
        val map = mapOf(Pair(0, 0) to 1, Pair(0, 1) to 2, Pair(1, 0) to 1, Pair(1, 1) to 1)
        assertThat(calculateOverlaps(map))
            .isEqualTo(1)
    }
}

