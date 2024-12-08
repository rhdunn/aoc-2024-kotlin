// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day07
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Tests : DayTest<Long>(Day07) {
    @Test
    fun part1() {
        assertEquals(3749, part1("day7/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(11387, part2("day7/example.txt"))
    }
}