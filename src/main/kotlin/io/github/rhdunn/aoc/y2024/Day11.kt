// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private fun String.parseArrangements(): List<Long> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        line.split("\\s+".toRegex()).map { it.toLong() }
    }.first()
}

private fun List<Long>.blink(): List<Long> {
    val result = mutableListOf<Long>()
    forEach { value ->
        val repr = value.toString()
        when {
            value == 0L -> result.add(1)

            repr.length % 2 == 0 -> {
                result.add(repr.substring(0, repr.length / 2).toLong())
                result.add(repr.substring(repr.length / 2).toLong())
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
