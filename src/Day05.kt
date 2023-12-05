fun main() {

    data class AlmanacRange(val targetStart: Long, val sourceStart: Long, val rangeLength: Long) {
        fun map(value: Long): Long? {
            return if (value >= sourceStart && value <= sourceStart + rangeLength)
                value - sourceStart + targetStart
            else
                null
        }
    }

    data class AlmanacMap(val source: String, val target: String, val ranges: List<AlmanacRange>) {
        fun findTarget(source: Long): Long {
            return ranges.firstNotNullOfOrNull { it.map(source) } ?: source
        }
    }

    data class Almanac(val seeds: List<Long>, val maps: List<AlmanacMap>) {
        fun findMap(sourceName: String): AlmanacMap? {
            return maps.find { it.source == sourceName }
        }
    }

    fun parse(input: List<String>): Almanac {
        val seeds = input.first().replace("seeds: ", "").split(" ").map { it.toLong() }
        var source: String? = null
        var target: String? = null
        val ranges = mutableListOf<AlmanacRange>()
        val maps = mutableListOf<AlmanacMap>()
        for (line in input.drop(2)) {
            if (line.contains("map:")) {
                val (header, _) = line.split(" ")
                val (from, to) = header.split("-to-")
                source = from
                target = to
            } else if (line.isNotBlank()) {
                val (targetStart, sourceStart, rangeLength) = line.split(" ").map { it.toLong() }
                ranges.add(AlmanacRange(targetStart, sourceStart, rangeLength))
            } else {
                maps.add(AlmanacMap(source!!, target!!, ranges.toList()))
                source = null
                target = null
                ranges.clear()
            }
        }
        if (source != null && target != null) {
            maps.add(AlmanacMap(source, target, ranges.toList()))
        }
        return Almanac(seeds, maps)
    }

    fun calculateLocation(almanac: Almanac, seed: Long): Long {
        var map: AlmanacMap? = almanac.findMap("seed")
        var result: Long = seed
        while (map != null) {
            result = map!!.findTarget(result)
            map = almanac.findMap(map!!.target)
        }
        return result
    }

    fun part1(input: List<String>): Long {
        val almanac = parse(input)
        val seeds = almanac.seeds
        val locations = seeds.map { seed -> calculateLocation(almanac, seed) }
        return locations.min()
    }

    fun part2(input: List<String>): Long {
        val almanac = parse(input)
        // bruteforce
        return almanac.seeds.windowed(2, 2)
            .parallelStream()
            .map { seeds -> LongRange(seeds[0], seeds[0] + seeds[1]).asSequence().map { seed -> calculateLocation(almanac, seed) }.min() }
            .toList()
            .min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val testResult = part1(testInput)
    check(testResult == 35L)

    val test2Input = readInput("Day05_test")
    val test2Result = part2(test2Input)
    test2Result.println()
    check(test2Result == 46L)

    // final result
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println() //556197164
}
