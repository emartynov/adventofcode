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

        assertThat(entry.event).isEqualTo(Event.ShiftStart(3137))
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

    @Test
    fun `Combine events into sleep map`() {
        val events = listOf(
            Entry(DateTime(1, 1, 1, 0, 0), Event.ShiftStart(1)),
            Entry(DateTime(1, 1, 1, 0, 34), Event.FallsAsleep),
            Entry(DateTime(1, 1, 1, 0, 36), Event.WakesUp),
            Entry(DateTime(1, 1, 1, 0, 45), Event.FallsAsleep),
            Entry(DateTime(1, 1, 1, 0, 50), Event.WakesUp)
        )

        val sleepMaps = events.toSleepMap()

        assertThat(sleepMaps.size).isEqualTo(1)
        assertThat(sleepMaps[1]!!.size).isEqualTo(1)
        assertThat(sleepMaps[1]!![0].id).isEqualTo(1)
        assertThat(sleepMaps[1]!![0].sleeps).isEqualTo(listOf(IntRange(34, 36), IntRange(45, 50)))
    }

    @Test
    fun `Combine guards' events`() {
        val events = listOf(
            Entry(DateTime(1, 1, 1, 0, 0), Event.ShiftStart(1)),
            Entry(DateTime(1, 1, 2, 0, 0), Event.ShiftStart(2))
        )

        val sleepMaps = events.toSleepMap()

        assertThat(sleepMaps.size).isEqualTo(2)
        assertThat(sleepMaps[1]!![0].id).isEqualTo(1)
        assertThat(sleepMaps[2]!![0].id).isEqualTo(2)
    }

    @Test
    fun `Unite sleep map for the guard`() {
        val sleepMaps = listOf(
            SleepMap(1, listOf(IntRange(0, 10), IntRange(40, 50))),
            SleepMap(1, listOf(IntRange(10, 20), IntRange(30, 40)))
        )

        val union = sleepMaps.combine()

        assertThat(union[0])
            .isEqualTo(
                SleepMap(
                    1,
                    listOf(IntRange(0, 10), IntRange(10, 20), IntRange(30, 40), IntRange(40, 50))
                )
            )
    }

    @Test
    fun `Find most sleepy guard`() {
        val guards = listOf(
            SleepMap(1, listOf(IntRange(0, 10), IntRange(40, 50))),
            SleepMap(2, listOf(IntRange(0, 5), IntRange(45, 50)))
        )

        val champion = findMostSleepy(guards)

        assertThat(champion.id).isEqualTo(1)
    }

    @Test
    fun `Find most slept minute`() {
        val sleep = SleepMap(1, listOf(IntRange(0, 10), IntRange(8, 10), IntRange(9, 11)))

        val mostSleptMinute = findMostSleptMinute(sleep)

        assertThat(mostSleptMinute).isEqualTo(9)
    }
}