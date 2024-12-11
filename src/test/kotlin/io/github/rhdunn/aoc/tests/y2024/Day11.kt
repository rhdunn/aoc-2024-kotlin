// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day11
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Tests : DayTest<Int>(Day11) {
    @Test
    fun part1() {
        assertEquals(55312, part1("day11/example.txt"))
    }
}
