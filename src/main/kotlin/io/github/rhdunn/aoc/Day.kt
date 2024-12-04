// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc

abstract class Day(val day: Int) {
    abstract fun part1(data: String): Int
    abstract fun part2(data: String): Int

    fun run(data: String, part: String): Int = when {
        part.endsWith("-1") -> part1(data)
        part.endsWith("-2") -> part2(data)
        else -> throw IllegalArgumentException("unknown day-part: $part")
    }
}

fun List<Day>.run(dayPart: String, data: String) {
    try {
        val day = dayPart.substringBefore('-').toIntOrNull()
            ?: throw IllegalArgumentException("unknown day-part: $dayPart")
        val handler = find { it.day == day } ?: throw IllegalArgumentException("unknown day: $dayPart")
        println(handler.run(data, dayPart))
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}
