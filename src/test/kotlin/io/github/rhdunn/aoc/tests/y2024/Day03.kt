// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day03
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Tests : DayTest<Int>(Day03) {
    @Test
    fun part1() {
        assertEquals(161, part1("day3/example.txt"))
    }

    @Test
    fun part2() {
        assertEquals(48, part2("day3/example2.txt"))
    }
}
