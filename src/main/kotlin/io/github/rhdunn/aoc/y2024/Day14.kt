// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.Position

private data class Velocity(val dx: Int, val dy: Int) {
    override fun toString(): String = "($dx,$dy)"
}

private data class Robot(var position: Position, val velocity: Velocity) {
    override fun toString(): String = "p=$position, v=$velocity"

    fun move(width: Int, height: Int) {
        val px = (position.x + velocity.dx + width) % width
        val py = (position.y + velocity.dy + height) % height
        position = Position(px, py)
    }

    companion object {
        private val FORMAT = "^p=([0-9]+),([0-9]+) v=(-?[0-9]+),(-?[0-9]+)$".toRegex()
        fun parseOrNull(input: String): Robot? = FORMAT.matchEntire(input)?.let { match ->
            val (px, py, vx, vy) = match.destructured
            Robot(Position(px.toInt(), py.toInt()), Velocity(vx.toInt(), vy.toInt()))
        }
    }
}

private fun String.parseRobots(): List<Robot> {
    return lineSequence().mapNotNull { Robot.parseOrNull(it) }.toList()
}

private fun List<Robot>.countInRegion(widths: IntRange, heights: IntRange): Int = count {
    it.position.x in widths && it.position.y in heights
}

private fun List<Robot>.findAt(x: Int, y: Int): Sequence<Robot> = asSequence().filter {
    it.position.x == x && it.position.y == y
}

private fun List<Robot>.inUniqueArrangement() : Boolean = all {
    findAt(it.position.x, it.position.y).count() == 1
}

// NOTE: For the puzzle input the args are [101, 103]
object Day14 : Day<Int>(14) {
    override fun part1(data: String, args: Array<String>): Int {
        if (args.size != 2) throw IllegalArgumentException("part 14 requires width and height arguments")
        val (width, height) = args.map { it.toInt() }

        val robots = data.parseRobots()
        repeat(100) {
            robots.forEach { it.move(width, height) }
        }

        val hw = width / 2
        val hh = height / 2

        val q1 = robots.countInRegion(0 until hw, 0 until hh)
        val q2 = robots.countInRegion((hw + 1) until width, 0 until hh)
        val q3 = robots.countInRegion(0 until hw, (hh + 1) until height)
        val q4 = robots.countInRegion((hw + 1) until width, (hh + 1) until height)
        return q1 * q2 * q3 * q4
    }

    override fun part2(data: String, args: Array<String>): Int {
        if (args.size != 2) throw IllegalArgumentException("part 14 requires width and height arguments")
        val (width, height) = args.map { it.toInt() }

        val robots = data.parseRobots()
        var seconds = 0
        while (!robots.inUniqueArrangement()) {
            robots.forEach { it.move(width, height) }
            ++seconds
        }
        return seconds
    }
}
