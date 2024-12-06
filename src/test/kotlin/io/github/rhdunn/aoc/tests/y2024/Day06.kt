// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day06
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Tests : DayTest(Day06) {
    @Test
    fun part1() {
        assertEquals(41, part1("day6/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(6, part2("day6/example.txt"))
    }
}
