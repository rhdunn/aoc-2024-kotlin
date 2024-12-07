// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc

import kotlin.time.Duration.Companion.milliseconds

abstract class Day<T>(val day: Int) {
    abstract fun part1(data: String): T
    abstract fun part2(data: String): T

    fun run(data: String, part: String): T = when {
        part.endsWith("-1") -> part1(data)
        part.endsWith("-2") -> part2(data)
        else -> throw IllegalArgumentException("unknown day-part: $part")
    }
}

fun List<Day<*>>.run(dayPart: String, data: String) {
    try {
        val day = dayPart.substringBefore('-').toIntOrNull()
            ?: throw IllegalArgumentException("unknown day-part: $dayPart")
        val handler = find { it.day == day } ?: throw IllegalArgumentException("unknown day: $dayPart")

        val startTime = System.currentTimeMillis()
        val result = handler.run(data, dayPart)
        val duration = (System.currentTimeMillis() - startTime).milliseconds

        println("answer $result took $duration to solve")
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}
