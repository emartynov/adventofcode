data class DateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
)

sealed class Event {
    object FallsAsleep : Event()
    object WakesUp : Event()
    data class ShiftStart(val guardId: String) : Event()
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
            Event.ShiftStart(eventString.substring(idStartIndex + 1, eventString.indexOf(' ', idStartIndex)))
        }
    }

    return Entry(DateTime(year, month, day, hour, minute), event)
}