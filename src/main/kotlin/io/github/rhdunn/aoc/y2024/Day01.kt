// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import kotlin.math.abs

private fun String.parseLists(): List<Pair<Int, Int>> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        val parts = line.split("\\s+".toRegex()).map { it.toInt() }
        parts[0] to parts[1]
    }.toList()
}

object Day01 : Day(1) {
    override fun part1(data: String): Int {
        return data.parseLists()
            .unzip().let { (a, b) -> a.sorted().zip(b.sorted()) } // pair up
            .sumOf { (a, b) -> abs(a - b) }
    }

    override fun part2(data: String): Int = 0
}
