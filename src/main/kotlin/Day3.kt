data class Claim(
    val id: Int,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

//"#1 @ 1,3: 4x4"
fun String.toClaim(): Claim {
    val parts = this.split(" ")

    return Claim(
        id = parseId(parts[0]),
        x = parseX(parts[2]),
        y = parseY(parts[2]),
        width = parseWidth(parts[3]),
        height = parseHeight(parts[3])
    )
}

private fun parseHeight(s: String) = s.substring(s.indexOf('x') + 1).toInt()

private fun parseWidth(s: String) = s.substring(0 until s.indexOf('x')).toInt()

private fun parseY(s: String) = s.substring(s.indexOf(',') + 1 until s.indexOf(':')).toInt()

private fun parseX(s: String): Int = s.substring(0 until s.indexOf(',')).toInt()

private fun parseId(s: String) = s.substring(1).trim().toInt()
