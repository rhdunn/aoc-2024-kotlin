// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private data class Grid(val data: List<String>) {
    override fun toString(): String = data.joinToString("\n")

    fun getOrNull(x: Int, y: Int) = data.getOrNull(y)?.getOrNull(x)

    val width: Int get() = data.firstOrNull()?.length ?: 0
    val widths: IntRange get() = 0 until width

    val height: Int get() = data.size
    val heights: IntRange get() = 0 until height

    companion object {
        fun parse(data: String): Grid = Grid(data.lines().filter { it.isNotBlank() })
    }
}

private fun Grid.horizontal(): Sequence<Char> = sequence {
    for (j in heights) { // top to bottom
        for (i in widths) { // left to right
            yield(getOrNull(i, j) ?: continue)
        }
        yield(' ')
    }
}

private fun Grid.vertical(): Sequence<Char> = sequence {
    for (i in widths) { // left to right
        for (j in heights) { // top to bottom
            yield(getOrNull(i, j) ?: continue)
        }
        yield(' ') // no matches across scan lines
    }
}

private fun Grid.diagonal(leftToRight: Boolean = true): Sequence<Char> = sequence {
    val dx = if (leftToRight) 1 else -1
    val dy = 1
    val scanX = if (leftToRight) widths.reversed() else widths
    // top right diagonals
    for (startX in scanX) {
        var i = startX
        var j = 0
        while (i in widths && j in heights) {
            yield(getOrNull(i, j) ?: continue)
            i += dx
            j += dy
        }
        yield(' ')
    }
    // bottom left diagonals, excluding the duplicate diagonal
    for (startY in 1 until height) {
        var i = scanX.last
        var j = startY
        while (i in widths && j in heights) {
            yield(getOrNull(i, j) ?: continue)
            i += dx
            j += dy
        }
        yield(' ')
    }
}

private fun String.findAllOffsets(value: String): List<Int> = indices.filter { i ->
    startsWith(value, i)
}

object Day04 : Day(4) {
    @Suppress("DuplicatedCode")
    override fun part1(data: String): Int {
        val grid = Grid.parse(data)
        val horizontal = grid.horizontal().joinToString("")
        val vertical = grid.vertical().joinToString("")
        val diagonalLTR = grid.diagonal(leftToRight = true).joinToString("")
        val diagonalRTL = grid.diagonal(leftToRight = false).joinToString("")

        var matches = 0
        matches += horizontal.findAllOffsets("XMAS").count()
        matches += horizontal.reversed().findAllOffsets("XMAS").count()
        matches += vertical.findAllOffsets("XMAS").count()
        matches += vertical.reversed().findAllOffsets("XMAS").count()
        matches += diagonalLTR.findAllOffsets("XMAS").count()
        matches += diagonalLTR.reversed().findAllOffsets("XMAS").count()
        matches += diagonalRTL.findAllOffsets("XMAS").count()
        matches += diagonalRTL.reversed().findAllOffsets("XMAS").count()
        return matches
    }

    override fun part2(data: String): Int {
        return 0
    }
}
