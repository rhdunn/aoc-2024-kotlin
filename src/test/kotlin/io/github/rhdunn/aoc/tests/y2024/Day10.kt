// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day10
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Tests : DayTest<Int>(Day10) {
    @Test
    fun part1() {
        assertEquals(36, part1("day10/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(81, part2("day10/example.txt"))
    }
}
