// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.Direction
import io.github.rhdunn.aoc.Grid

private data class TrailPosition(val x: Int, val y: Int, val height: Int)

private fun Grid<Int>.next(position: TrailPosition): Sequence<TrailPosition> = sequence {
    Direction.entries.forEach { direction ->
        val newX = position.x + direction.dx
        val newY = position.y + direction.dy
        if (newX in widths && newY in heights && get(newX, newY) == position.height + 1) {
            yield(TrailPosition(newX, newY, position.height + 1))
        }
    }
}

private fun Grid<Int>.trailHeadScore(trailHead: TrailPosition): Int {
    val positions = mutableListOf<TrailPosition>()
    val visited = mutableSetOf<TrailPosition>()
    var score = 0

    next(trailHead).forEach { positions.add(it) }
    while (positions.isNotEmpty()) {
        val c = positions.removeAt(0)
        if (c in visited) continue

        visited.add(c)
        if (c.height == 9) {
            ++score
        } else {
            next(c).forEach { positions.add(it) }
        }
    }

    return score
}

object Day10 : Day<Int>(10) {
    override fun part1(data: String): Int {
        val map = Grid.parse(data) { it - '0' }
        return map.indices
            .filter { (headX, headY) -> map[headX, headY] == 0 }
            .sumOf { (headX, headY) ->
                map.trailHeadScore(TrailPosition(headX, headY, 0))
            }
    }

    override fun part2(data: String): Int {
        return 0
    }
}
