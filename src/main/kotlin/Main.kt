import java.io.File


fun main() {
    val inputFile = ResourcesHelper.getFileFromResources(1)

    println(calculateDay1CollectSolution(inputFile))
    println(calculateDay1FrequencySolution(inputFile))
}

class ResourcesHelper {
    companion object {
        fun getFileFromResources(day: Int): File {
            val url = ResourcesHelper::class.java.getResource("input$day.txt")
            return File(url.path)
        }
    }
}