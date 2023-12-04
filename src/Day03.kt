fun main() {

    data class Number(val number: Long, val coordinates: List<Pair<Int, Int>>, var partNumber: Boolean = false)
    data class Character(val character: Char, val coordinates: Pair<Int, Int>) {
        val neighbourhood = listOf(
            Pair(coordinates.first - 1, coordinates.second - 1),
            Pair(coordinates.first, coordinates.second - 1),
            Pair(coordinates.first + 1, coordinates.second - 1),
            Pair(coordinates.first - 1, coordinates.second),
            Pair(coordinates.first + 1, coordinates.second),
            Pair(coordinates.first - 1, coordinates.second + 1),
            Pair(coordinates.first, coordinates.second + 1),
            Pair(coordinates.first + 1, coordinates.second + 1)
        )
        fun isNearby(number: Number): Boolean {
            for (numberCharCoordinate in number.coordinates) {
                if (neighbourhood.contains(numberCharCoordinate)) {
                    return true
                }
            }
            return false
        }
    }
    class Engine(schematic: List<String>) {

        val matrix: List<List<Char>> = schematic.map(String::toList)
        val partNumbers = mutableListOf<Number>()
        val characters = mutableListOf<Character>()

        init {
            for ((y, row) in matrix.withIndex()) {
                val tmpNumber = mutableListOf<Char>()
                val tmpNumberCoordinates = mutableListOf<Pair<Int, Int>>()
                for ((x, cell) in row.withIndex()) {
                    if (cell.isDigit()) {
                        tmpNumber.add(cell)
                        tmpNumberCoordinates.add(Pair(x, y))
                    }
                    if (isSymbol(cell)) {
                        characters.add(Character(cell, Pair(x, y)))
                    }
                    if (x + 1 >= row.size || !(row[x + 1].isDigit())) {
                        if (tmpNumber.isNotEmpty()) {
                            partNumbers.add(Number(tmpNumber.joinToString("").toLong(), tmpNumberCoordinates.toList()))
                        }
                        tmpNumber.clear()
                        tmpNumberCoordinates.clear()
                    }
                }
            }

            for (character in characters) {
                for (number in partNumbers) {
                    if (!number.partNumber && character.isNearby(number)) {
                        number.partNumber = true
                    }
                }
            }

        }

        private fun isSymbol(c: Char) = !(c.isDigit() || c == '.')

        fun gears(): List<Pair<Character, List<Number>>> {
            return characters
                .filter { character -> character.character == '*' }
                .map { character -> character to partNumbers.filter { number -> character.isNearby(number) } }
                .filter { (_, numbers) -> numbers.size == 2 }
        }
    }

    fun part1(input: List<String>): Long {
        val engine = Engine(input)
        return engine.partNumbers.filter { it.partNumber }.sumOf(Number::number)
    }

    fun part2(input: List<String>): Long {
        val engine = Engine(input)
        return engine.gears().sumOf { it.second.fold(1L) { l, r -> l * r.number } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testResult = part1(testInput)
    check(testResult == 4361L)

    val test2Input = readInput("Day03_test")
    val test2Result = part2(test2Input)
    check(test2Result == 467835L)

    // final result
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
