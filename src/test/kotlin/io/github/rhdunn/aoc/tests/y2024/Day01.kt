// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day01
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Tests : DayTest<Int>(Day01) {
    @Test
    fun part1() {
        assertEquals(11, part1("day1/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(31, part2("day1/example.txt"))
    }
}
