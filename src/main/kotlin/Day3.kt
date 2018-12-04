import java.io.File

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

fun buildMap(claims: List<Claim>): Map<Pair<Int, Int>, Int> {
    val result = mutableMapOf<Pair<Int, Int>, Int>()

    claims.forEach { claim ->
        for (i in 0 until claim.width) {
            for (j in 0 until claim.height) {
                val coords = Pair(claim.x + i, claim.y + j)
                val claimedNumber = result[coords] ?: 0
                result[coords] = claimedNumber + 1
            }
        }
    }

    return result
}

fun calculateOverlaps(mapOfClaims: Map<Pair<Int, Int>, Int>) = mapOfClaims.map { entry -> entry.value }
    .filter { it > 1 }
    .count()

fun calculateOverlappedFromFile(file: File): Int {
    val claims = file.readLines().map { it.toClaim() }
    return calculateOverlaps(buildMap(claims))
}

fun Claim.overlaps(mapOfClaims: Map<Pair<Int, Int>, Int>): Boolean {
    for (i in 0 until this.width) {
        for (j in 0 until this.height) {
            val coords = Pair(this.x + i, this.y + j)
            val claimedNumber = mapOfClaims[coords] ?: 0

            if (claimedNumber > 1) return true
        }
    }

    return false
}

fun findNotOverlappedFromFile(file: File): Int {
    val claims = file.readLines().map { it.toClaim() }
    val map = buildMap(claims)

    for (claim in claims) {
        if (!claim.overlaps(map)) return claim.id
    }

    return -1
}

