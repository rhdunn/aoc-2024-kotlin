// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import kotlin.math.abs

private fun String.parseReports(): List<List<Int>> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        line.split("\\s+".toRegex()).map { it.toInt() }
    }.toList()
}

private fun List<Int>.isSafe(): Boolean = zipWithNext().let { paired ->
    val decreasing = paired.all { (a, b) -> a < b }
    val increasing = paired.all { (a, b) -> a > b }
    val difference = paired.maxOfOrNull { (a, b) -> abs(b - a) } ?: 0
    return (decreasing || increasing) && difference <= 3
}

private fun List<Int>.isSafeWithDampener(): Boolean = indices.any { i ->
    val filtered = withIndex().filter { it.index != i }.map { it.value }
    filtered.isSafe()
}

object Day02 : Day<Int>(2) {
    override fun part1(data: String, args: Array<String>): Int {
        return data.parseReports().count { it.isSafe() }
    }

    override fun part2(data: String, args: Array<String>): Int {
        return data.parseReports().count {
            it.isSafe() || it.isSafeWithDampener()
        }
    }
}
