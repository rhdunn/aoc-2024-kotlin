// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day05
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Tests : DayTest(Day05) {
    @Test
    fun part1() {
        assertEquals(143, part1("day5/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(123, part2("day5/example.txt"))
    }
}
