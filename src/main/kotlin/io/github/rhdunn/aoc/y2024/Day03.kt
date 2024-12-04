// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private fun String.parseMuls(): List<Pair<Int, Int>> {
    return """mul\((\d+),(\d+)\)""".toRegex().findAll(this).map { match ->
        match.groupValues[1].toInt() to match.groupValues[2].toInt()
    }.toList()
}

object Day03 : Day(3) {
    override fun part1(data: String): Int {
        return data.parseMuls().sumOf { (a, b) -> a * b }
    }

    override fun part2(data: String): Int {
        return 0
    }
}
