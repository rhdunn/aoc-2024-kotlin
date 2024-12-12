// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day12
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Tests : DayTest<Int>(Day12) {
    @Test
    fun part1a() {
        assertEquals(140, part1("day12/example1.txt"))
    }

    @Test
    fun part1b() {
        assertEquals(1930, part1("day12/example2.txt"))
    }
}
