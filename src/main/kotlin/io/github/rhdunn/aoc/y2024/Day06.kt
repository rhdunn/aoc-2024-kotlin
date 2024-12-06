// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private data class Map(val data: List<MutableList<Char>>) {
    override fun toString(): String = data.joinToString("\n") {
        it.joinToString("")
    }

    operator fun get(x: Int, y: Int) = data[y][x]
    fun getOrNull(x: Int, y: Int) = data.getOrNull(y)?.getOrNull(x)

    operator fun set(x: Int, y: Int, value: Char) {
        data[y][x] = value
    }

    val width: Int get() = data.firstOrNull()?.size ?: 0
    val widths: IntRange get() = 0 until width

    val height: Int get() = data.size
    val heights: IntRange get() = 0 until height

    val indices: Sequence<Pair<Int, Int>>
        get() = sequence {
            for (y in heights) {
                for (x in widths) {
                    yield(x to y)
                }
            }
        }

    companion object {
        fun parse(data: String): Map {
            return Map(data.lines().filter { it.isNotBlank() }.map { it.toMutableList() })
        }
    }
}

private enum class Direction(val symbol: Char, val dx: Int, val dy: Int) {
    Up('^', 0, -1),
    Down('v', 0, 1),
    Left('<', -1, 0),
    Right('>', 1, 0);

    val turnRight: Direction
        get() = when (this) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
        }

    companion object {
        const val Symbols = "^v<>"

        fun valueOfOrNull(symbol: Char): Direction? = when (symbol) {
            Up.symbol -> Up
            Down.symbol -> Down
            Left.symbol -> Left
            Right.symbol -> Right
            else -> null
        }
    }
}

private data class Guard(val x: Int, val y: Int, val direction: Direction, val below: Char) {
    fun move(map: Map): Guard {
        var newDirection = direction
        var newX = x + direction.dx
        var newY = y + direction.dy
        while (map.getOrNull(newX, newY) == '#') { // obstruction
            newDirection = newDirection.turnRight
            newX = x + newDirection.dx
            newY = y + newDirection.dy
            if (newDirection == direction) throw IllegalStateException("guard trapped!")
        }
        return Guard(newX, newY, newDirection, map.getOrNull(newX, newY) ?: '.')
    }

    companion object {
        fun location(map: Map): Guard {
            return map.indices
                .find { (x, y) -> map[x, y] in Direction.Symbols }!!
                .let { (x, y) -> Guard(x, y, Direction.valueOfOrNull(map[x, y])!!, '.') }
        }
    }
}

object Day06 : Day(6) {
    override fun part1(data: String): Int {
        val map = Map.parse(data)
        var guard = Guard.location(map)
        var visited = 0
        while (guard.x in map.widths && guard.y in map.heights) {
            val newGuard = guard.move(map)
            if (newGuard.below == '.') {
                ++visited
            }

            map[guard.x, guard.y] = 'X'
            guard = newGuard
        }

        return visited
    }

    override fun part2(data: String): Int {
        return 0
    }
}
