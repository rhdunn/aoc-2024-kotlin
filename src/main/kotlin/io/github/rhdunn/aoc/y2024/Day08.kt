// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.Grid
import io.github.rhdunn.aoc.Position

private fun Grid<Char>.antenna(): Sequence<Position> {
    return indices
        .filter { (x, y) ->
            val loc = getOrNull(x, y)
            loc != null && loc != '.'
        }
        .map { (x, y) -> Position(x, y) }
}

private fun antinodeCandidates(a: Position, b: Position): Sequence<Position> = sequence {
    val dx = b.x - a.x
    val dy = b.y - a.y
    yield(Position(a.x + dx, a.y + dy))
    yield(Position(a.x - dx, a.y - dy))
    yield(Position(b.x + dx, b.y + dy))
    yield(Position(b.x - dx, b.y - dy))
}

private fun Grid<Char>.antinodes(a: Position, b: Position): Sequence<Position> {
    return antinodeCandidates(a, b)
        .filter { it != a && it != b } // no overlap
        .filter { (x, y) -> x in widths && y in heights } // within city limits
}

private fun Grid<Char>.pointsInLine(a: Position, b: Position): Sequence<Position> = sequence {
    val dx = b.x - a.x
    val dy = b.y - a.y
    if (dx == 0 && dy == 0) return@sequence

    var ox = a.x
    var oy = a.y
    while (ox in widths && oy in heights) {
        yield(Position(ox, oy))
        ox += dx
        oy += dy
    }

    ox = a.x - dx
    oy = a.y - dy
    while (ox in widths && oy in heights) {
        yield(Position(ox, oy))
        ox -= dx
        oy -= dy
    }
}

object Day08 : Day<Int>(8) {
    override fun part1(data: String, args: Array<String>): Int {
        val city = Grid.parse(data)
        val antenna = city.antenna().groupBy { (x, y) -> city[x, y] }

        val antinodes = mutableSetOf<Position>()
        antenna.keys.forEach { strength ->
            val candidates = antenna[strength]!!
            candidates.forEach { a ->
                candidates.forEach { b -> antinodes.addAll(city.antinodes(a, b)) }
            }
        }

        return antinodes.size
    }

    override fun part2(data: String, args: Array<String>): Int {
        val city = Grid.parse(data)
        val antenna = city.antenna().groupBy { (x, y) -> city[x, y] }

        val antinodes = mutableSetOf<Position>()
        antenna.keys.forEach { strength ->
            val candidates = antenna[strength]!!
            candidates.forEach { a ->
                candidates.forEach { b -> antinodes.addAll(city.pointsInLine(a, b)) }
            }
        }

        return antinodes.size
    }
}
