// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.Grid

private object Map {
    const val Directions = "^v<>"

    const val NewObstruction = 'O'
    const val Obstacles = "#O"

    const val Unvisited = '.'
    const val Visited = 'X'
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
    constructor(x: Int, y: Int, direction: Direction, map: Grid<Char>) : this(x, y, direction) {
        below = map.getOrNull(x, y) ?: Map.Unvisited
    }

    var below: Char = Map.Unvisited
        private set

    fun move(map: Grid<Char>): Guard {
        return this.canMoveTo(map).firstOrNull() ?: throw IllegalStateException("guard trapped!")
    }

    override fun toString(): String = "${direction.symbol}($x,$y)"

    companion object {
        fun location(map: Grid<Char>): Guard {
            return map.indices
                .find { (x, y) -> map[x, y] in Map.Directions }!!
                .let { (x, y) -> Guard(x, y, Direction.valueOfOrNull(map[x, y])!!) }
        }
    }
}

private fun Guard.canMoveTo(map: Grid<Char>): Sequence<Guard> = sequence {
    var newDirection = direction
    do {
        val newGuard = Guard(x + newDirection.dx, y + newDirection.dy, newDirection, map)
        if (newGuard.below !in Map.Obstacles) {
            yield(newGuard)
        }
        newDirection = newDirection.turnRight
    } while (newDirection != direction)
}

private fun Guard.isStuckInLoop(map: Grid<Char>, obstruction: Guard): Boolean {
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

private fun Guard.tracePath(map: Grid<Char>): Set<Guard> {
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

object Day06 : Day<Int>(6) {
    override fun part1(data: String): Int {
        val map = Grid.parse(data)
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
        val map = Grid.parse(data)
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
