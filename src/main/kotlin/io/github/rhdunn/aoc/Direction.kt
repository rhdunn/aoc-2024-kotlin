// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc

enum class Direction(val symbol: Char, val dx: Int, val dy: Int) {
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

    val opposite: Direction
        get() = when (this) {
            Up -> Down
            Right -> Left
            Down -> Up
            Left -> Right
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
