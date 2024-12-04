// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests

import io.github.rhdunn.aoc.Day

open class DayTest(val day: Day) {
    fun load(filename: String): String {
        val resource = this.javaClass.classLoader.getResourceAsStream(filename)!!
        return resource.bufferedReader().use { it.readText() }
    }

    fun part1(filename: String): Int = day.part1(load(filename))
    fun part2(filename: String): Int = day.part2(load(filename))
}
