import kotlin.math.min

fun main() {

    data class Card(val winning: List<Int>, val yours: List<Int>) {
        fun points() = yours.intersect(winning.toSet())
            .fold(1) { a, _ -> a * 2} / 2

        fun winnings() = yours.intersect(winning.toSet()).size
    }

    fun buildCards(input: List<String>): List<Card> {
        val cards = input.map { line ->
            val (_, content) = line.split(": ")
            val (winningRaw, yoursRaw) = content.split(" | ")
            val winning = winningRaw.split(" ").filter { it.isNotBlank() }.map(String::toInt)
            val yours = yoursRaw.split(" ").filter { it.isNotBlank() }.map(String::toInt)
            Card(winning, yours)
        }
        return cards
    }

    fun part1(input: List<String>): Long {
        val cards = buildCards(input)
        return cards.sumOf { it.points().toLong() }
    }

    fun part2(input: List<String>): Long {
        val cards = buildCards(input)
        val counters = List(cards.size) { index -> index to 1}.toMap().toMutableMap()
        for (index in cards.indices) {
            val card = cards[index]
            val count = counters[index]!!
            val winnings = card.winnings()
            val start = index + 1
            val end = min(winnings + index, cards.size - 1)
            (start..end).forEach {
                counters[it] =  (counters[it]!! + count)
            }
        }
        return counters.values.sum().toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testResult = part1(testInput)
    check(testResult == 13L)

    val test2Input = readInput("Day04_test")
    val test2Result = part2(test2Input)
    check(test2Result == 30L)

    // final result
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
