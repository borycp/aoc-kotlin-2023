import kotlin.math.max

fun main() {
    data class Game(val id: Int, val cubeSet: List<Map<String, Int>>) {
        fun power() = cubeSet.fold(mutableMapOf<String, Int>()) { acc, round ->
            round.forEach { (color, count) ->
                acc[color] = max(acc.getOrDefault(color, 0), count)
            }
            acc
        }.values.fold(1, Int::times)


    }

    fun fromLine(line: String): Game {
        val (idPart, setPart) = line.split(": ")
        val id = idPart.replace("Game ", "").toInt()
        val set = setPart.split("; ").map {play ->
            play.split(", ").associate { cubeCount ->
                val (number, color) = cubeCount.split(" ")
                color to number.toInt()
            }
        }
        return Game(id, set)
    }


    fun part1(input: List<String>): Int {
        val games = input.map { fromLine(it) }
        val assumption = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val matchingGames = games.filter { game ->
            game.cubeSet.all { play ->
                play.all { (color, count) ->
                    count <= (assumption[color] ?: 0)
                }
            }
        }
        return matchingGames.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map { fromLine(it) }
        return games.sumOf { it.power() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testResult = part1(testInput)
    check(testResult == 8)

    val test2Input = readInput("Day02_test")
    val test2Result = part2(test2Input)
    check(test2Result == 2286)

    // final result
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
