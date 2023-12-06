import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {

    fun countWinsNumber(time: Long, distance: Long): Int {
        val delta = time * time - 4 * distance
        val min = floor(((time - sqrt(delta.toDouble())) / 2) + 1)
        val max = ceil(((time + sqrt(delta.toDouble())) / 2) - 1)
        return max.toInt() - min.toInt() + 1
    }

    fun part1(input: List<String>): Int {
        val times = input.first()
            .replace("Time:", "")
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
        val distances = input.last()
            .replace("Distance:", "")
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        return times.indices.map { i ->
            val time = times[i]
            val distance = distances[i]
            countWinsNumber(time, distance)
        }.fold(1, Int::times)
    }

    fun part2(input: List<String>): Int {
        val time = input.first()
            .replace("Time:", "")
            .trim()
            .replace(" ", "")
            .toLong()
        val distance = input.last()
            .replace("Distance:", "")
            .trim()
            .replace(" ", "")
            .toLong()
        return countWinsNumber(time, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val testResult = part1(testInput)
    check(testResult == 288)

    val test2Input = readInput("Day06_test")
    val test2Result = part2(test2Input)
    test2Result.println()
    check(test2Result == 71503)

    // final result
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
