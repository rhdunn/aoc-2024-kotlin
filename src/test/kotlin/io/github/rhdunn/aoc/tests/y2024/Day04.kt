// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day04
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Tests : DayTest(Day04) {
    @Test
    fun part1() {
        assertEquals(18, part1("day4/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(9, part2("day4/example.txt"))
    }
}
