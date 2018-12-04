import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day4Should {
    @Test
    fun `Parse entry time line correctly`() {
        val line = "[1518-05-10 00:02] Guard #3137 begins shift"

        val entry = line.toGuardEntry()

        assertThat(entry.dateTime).isEqualTo(DateTime(1518, 5, 10, 0, 2))
    }

    @Test
    fun `Parse guard begin event with id correctly`() {
        val line = "[1518-05-10 00:02] Guard #3137 begins shift"

        val entry = line.toGuardEntry()

        assertThat(entry.event).isEqualTo(Event.ShiftStart("3137"))
    }

    @Test
    fun `Parse guard falls asleep event correctly`() {
        val line = "[1518-05-10 00:02] falls asleep"

        val entry = line.toGuardEntry()

        assertThat(entry.event).isEqualTo(Event.FallsAsleep)
    }

    @Test
    fun `Parse guard wakes up event correctly`() {
        val line = "[1518-05-10 00:02] wakes up"

        val entry = line.toGuardEntry()

        assertThat(entry.event).isEqualTo(Event.WakesUp)
    }
}