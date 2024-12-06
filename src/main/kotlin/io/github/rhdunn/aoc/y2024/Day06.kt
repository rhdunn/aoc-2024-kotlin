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
        const val Directions = "^v<>"

        const val NewObstruction = 'O'
        const val Obstacles = "#O"

        const val Unvisited = '.'
        const val Visited = 'X'

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
        fun valueOfOrNull(symbol: Char): Direction? = when (symbol) {
            Up.symbol -> Up
            Down.symbol -> Down
            Left.symbol -> Left
            Right.symbol -> Right
            else -> null
        }
    }
}

private data class Guard(val x: Int, val y: Int, val direction: Direction) {
    constructor(x: Int, y: Int, direction: Direction, map: Map) : this(x, y, direction) {
        below = map.getOrNull(x, y) ?: Map.Unvisited
    }

    var below: Char = Map.Unvisited
        private set

    fun move(map: Map): Guard {
        return this.canMoveTo(map).firstOrNull() ?: throw IllegalStateException("guard trapped!")
    }

    override fun toString(): String = "${direction.symbol}($x,$y)"

    companion object {
        fun location(map: Map): Guard {
            return map.indices
                .find { (x, y) -> map[x, y] in Map.Directions }!!
                .let { (x, y) -> Guard(x, y, Direction.valueOfOrNull(map[x, y])!!) }
        }
    }
}

private fun Guard.canMoveTo(map: Map): Sequence<Guard> = sequence {
    var newDirection = direction
    do {
        val newGuard = Guard(x + newDirection.dx, y + newDirection.dy, newDirection, map)
        if (newGuard.below !in Map.Obstacles) {
            yield(newGuard)
        }
        newDirection = newDirection.turnRight
    } while (newDirection != direction)
}

private fun Guard.isStuckInLoop(map: Map, obstruction: Guard): Boolean {
    var guard = this

    val saved = map.getOrNull(obstruction.x, obstruction.y) ?: return false
    map[obstruction.x, obstruction.y] = Map.NewObstruction

    val visited = mutableSetOf<Guard>()
    while (guard.x in map.widths && guard.y in map.heights) {
        val nextGuard = guard.move(map)
        if (nextGuard in visited) {
            map[obstruction.x, obstruction.y] = saved
            return true // guard has visited this location in this direction so will loop
        }
        guard = nextGuard
        visited.add(guard)
    }

    map[obstruction.x, obstruction.y] = saved
    return false // guard can exit the map
}

private fun Guard.tracePath(map: Map): Set<Guard> {
    var guard = this
    val visited = mutableSetOf<Guard>()
    while (guard.x in map.widths && guard.y in map.heights) {
        if (guard in visited) {
            break
        }
        visited.add(guard)
        guard = guard.move(map)
    }
    return visited
}

object Day06 : Day(6) {
    override fun part1(data: String): Int {
        val map = Map.parse(data)
        var guard = Guard.location(map)

        var visited = 0
        while (guard.x in map.widths && guard.y in map.heights) {
            val newGuard = guard.move(map)
            if (newGuard.below == Map.Unvisited) {
                ++visited
            }

            map[guard.x, guard.y] = Map.Visited
            guard = newGuard
        }

        return visited
    }

    override fun part2(data: String): Int {
        val map = Map.parse(data)
        var guard = Guard.location(map)
        val paths = guard.tracePath(map)

        val obstructions = mutableSetOf<Pair<Int, Int>>()
        paths.forEach { newGuard ->
            val position = newGuard.canMoveTo(map).firstOrNull()
                ?.takeUnless { it.x == guard.x && it.y == guard.y } // exclude starting location
            if (position != null && guard.isStuckInLoop(map, position)) {
                val newObstruction = position.let { (x, y) -> x to y }
                if (newObstruction !in obstructions) {
                    obstructions.add(newObstruction)
                }
            }
        }

        return obstructions.count()
    }
}
