// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private fun String.parseArrangements(): List<Long> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        line.split("\\s+".toRegex()).map { it.toLong() }
    }.first()
}

private val Long.digits: Int get() {
    var digits = 0
    var current = this
    while (current > 0L) {
        ++digits
        current /= 10
    }
    return digits
}

private fun Long.split(n: Int): Pair<Long, Long> {
    var value = this
    repeat(n) { value /= 10 }

    var mask = value
    repeat(n) { mask *= 10 }

    return value to (this - mask)
}

private fun List<Long>.blink(): List<Long> {
    val result = mutableListOf<Long>()
    forEach { value ->
        val digits = value.digits
        when {
            value == 0L -> result.add(1)

            digits % 2 == 0 -> {
                val (left, right) = value.split(digits / 2)
                result.add(left)
                result.add(right)
            }

            else -> result.add(value * 2024)
        }
    }
    return result
}

object Day11 : Day<Int>(11) {
    override fun part1(data: String): Int {
        var arrangements = data.parseArrangements()
        val blinks = 25
        repeat(blinks) {
            arrangements = arrangements.blink()
        }
        return arrangements.size
    }

    override fun part2(data: String): Int {
        return 0
    }
}
