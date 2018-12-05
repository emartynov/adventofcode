import java.io.File

data class DateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
) : Comparable<DateTime> {
    override fun compareTo(other: DateTime): Int {
        return when {
            this.year != other.year -> (this.year - other.year) * 100 * 100 * 100 * 1000
            this.month != other.month -> (this.month - other.month) * 100 * 100 * 100
            this.day != other.day -> (this.day - other.day) * 100 * 100
            this.hour != other.hour -> (this.hour - other.hour) * 100
            else -> (this.minute - other.minute)
        }
    }
}

sealed class Event {
    object FallsAsleep : Event()
    object WakesUp : Event()
    data class ShiftStart(val guardId: Int) : Event()
}

data class Entry(val dateTime: DateTime, val event: Event)

fun String.toGuardEntry(): Entry {
    val dateTimeEndIndex = this.indexOf(']')

    val dateString = this.substring(1, dateTimeEndIndex)
    val year = dateString.substring(0, 4).toInt()
    val month = dateString.substring(5, 7).toInt()
    val day = dateString.substring(8, 10).toInt()
    val hour = dateString.substring(11, 13).toInt()
    val minute = dateString.substring(14, 16).toInt()

    val eventString = this.substring(dateTimeEndIndex + 2)
    val event = when (eventString) {
        "falls asleep" -> Event.FallsAsleep
        "wakes up" -> Event.WakesUp
        else -> {
            val idStartIndex = eventString.indexOf('#')
            val isEndIndex = eventString.indexOf(' ', idStartIndex)
            val isString = eventString.substring(idStartIndex + 1, isEndIndex)
            Event.ShiftStart(isString.toInt())
        }
    }

    return Entry(DateTime(year, month, day, hour, minute), event)
}

data class SleepMap(
    val id: Int,
    val sleeps: List<IntRange>
)

fun List<Entry>.toSleepMap(): List<SleepMap> {
    val result = mutableListOf<SleepMap>()

    var id = -1
    val sleeps = mutableListOf<IntRange>()
    var startMinute = 0
    for (entry in this) {
        when (entry.event) {
            is Event.ShiftStart -> {
                addSleepMap(result, id, sleeps)
                id = entry.event.guardId
                sleeps.clear()
            }
            is Event.FallsAsleep -> startMinute = entry.dateTime.minute
            is Event.WakesUp -> sleeps.add(IntRange(startMinute, entry.dateTime.minute))
        }
    }
    addSleepMap(result, id, sleeps)

    return result
}

private fun addSleepMap(
    result: MutableList<SleepMap>,
    id: Int,
    sleeps: MutableList<IntRange>
) {
    if (id != -1) {
        result.add(SleepMap(id, sleeps))
    }
}

fun List<SleepMap>.combine(): List<SleepMap> =
    this.groupBy { it.id }
        .mapValues { it -> SleepMap(it.key, combineSleeps(it.value)) }
        .values
        .toList()

fun combineSleeps(list: List<SleepMap>) = list.map { it.sleeps }
    .flatten()
    .sortedBy { it.first }

fun findMostSleepy(sleeps: List<SleepMap>): SleepMap {
    var result = SleepMap(-1, emptyList())
    var maxSleptMinutes = 0

    for (sleep in sleeps) {
        val sleptMinutes = calculateSleptMinutes(sleep.sleeps)
        if (sleptMinutes > maxSleptMinutes) {
            maxSleptMinutes = sleptMinutes
            result = sleep
        }
    }

    return result
}

private fun calculateSleptMinutes(sleeps: List<IntRange>): Int {
    if (sleeps.isEmpty())
        return 0
    else {
        val combined = combineRanges(sleeps)
        return combined.map { it.last - it.first }.sum()
    }
}

private fun combineRanges(ranges: List<IntRange>): List<IntRange> {
    val result = mutableListOf<IntRange>()
    var currentRange = ranges[0]

    for (i in 1 until ranges.size) {
        val nextRange = ranges[i]
        currentRange = if (nextRange.first > currentRange.last) {
            result.add(currentRange)
            nextRange
        } else {
            IntRange(currentRange.start, Math.max(currentRange.last, nextRange.last))
        }
    }
    result.add(currentRange)

    return result
}

fun findMostSleptMinute(sleep: SleepMap): Int {
    val minutesMap = mutableMapOf<Int, Int>()

    for (interval in sleep.sleeps) {
        for (i in interval) {
            val times = minutesMap[i] ?: 0
            minutesMap[i] = times + 1
        }
    }

    return minutesMap.maxBy { it -> it.value }!!.key
}

fun findIDxMinute(file: File): Int {
    val sleeps = file.readLines().map { it.toGuardEntry() }.sortedBy { it.dateTime }.toSleepMap()
    val mostSleepyGuard = findMostSleepy(sleeps)
    val mostSleptMinute = findMostSleptMinute(mostSleepyGuard)
    return mostSleepyGuard.id * mostSleptMinute
}

