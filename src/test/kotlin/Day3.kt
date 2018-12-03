import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day3Should {
    @Test
    fun `Parse claim from string`() {
        assertThat("#1 @ 1,3: 4x4".toClaim()).isEqualTo(Claim(id = 1, x = 1, y = 3, width = 4, height = 4))
    }
}

