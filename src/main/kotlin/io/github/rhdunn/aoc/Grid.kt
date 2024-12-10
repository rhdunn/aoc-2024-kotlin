// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc

data class Grid(val data: List<MutableList<Char>>) {
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
        fun parse(data: String): Grid {
            return Grid(data.lines().filter { it.isNotBlank() }.map { it.toMutableList() })
        }
    }
}
