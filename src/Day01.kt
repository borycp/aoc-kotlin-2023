fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.filter { char -> char.isDigit() } }
            .map { it.first().toString() + it.last().toString() }
            .sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val digits = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4,
            "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
        )

        fun digitalize(input: String) : String {
            if (input.isEmpty()) return input
            for (digit in digits) {
                if (input.startsWith(digit.key) || input.startsWith(digit.value.toString())) {
                    return digit.value.toString() + digitalize(input.drop(1))
                }
            }
            return "" + digitalize(input.drop(1))
        }

        return input
            .map { line -> digitalize(line) }
            .map { it.filter { char -> char.isDigit() } }
            .map { it.first().toString() + it.last().toString() }
            .sumOf { it.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testResult = part1(testInput)
    check(testResult == 142)

    val test2Input = readInput("Day01_test2")
    val test2Result = part2(test2Input)
    test2Result.println()
    check(test2Result == 281)

    // final result
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
    //54824
}
