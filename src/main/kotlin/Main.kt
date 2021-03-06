import java.io.File


fun main() {
    println("Day1")
    val inputFile1 = ResourcesHelper.getFileFromResources(1)
    println("Result frequency: ${calculateDay1CollectSolution(inputFile1)}")
    println("Repeated frequency: ${calculateDay1FrequencySolution(inputFile1)}")

    println("Day2")
    val inputFile2 = ResourcesHelper.getFileFromResources(2)
    println("Checksum: ${calculateFileChecksum(inputFile2)}")
    println("Non changed: ${onePairNonChangedChars(inputFile2)}")

    println("Day3")
    val inputFile3 = ResourcesHelper.getFileFromResources(3)
    println("Overclaimed: ${calculateOverlappedFromFile(inputFile3)}")
    println("Not overlaped id: ${findNotOverlappedFromFile(inputFile3)}")

    println("Day4")
    val inputFile4 = ResourcesHelper.getFileFromResources(4)
    println("IDxMinute:${findIDxMinute(inputFile4)}")
}

class ResourcesHelper {
    companion object {
        fun getFileFromResources(day: Int): File {
            val url = ResourcesHelper::class.java.getResource("input$day.txt")
            return File(url.path)
        }
    }
}