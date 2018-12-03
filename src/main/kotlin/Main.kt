import java.io.File


fun main() {
    println("Day1")
    val inputFile1 = ResourcesHelper.getFileFromResources(1)
    println("Result frequency: ${calculateDay1CollectSolution(inputFile1)}")
    println("Repeated frequency: ${calculateDay1FrequencySolution(inputFile1)}")

    println("Day2")
    val inputFile2 = ResourcesHelper.getFileFromResources(2)
    println("Checksum: ${calculateFileChecksum(inputFile2)}")
}

class ResourcesHelper {
    companion object {
        fun getFileFromResources(day: Int): File {
            val url = ResourcesHelper::class.java.getResource("input$day.txt")
            return File(url.path)
        }
    }
}