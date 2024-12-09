// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day09
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Tests : DayTest<Long>(Day09) {
    @Test
    fun part1() {
        assertEquals(1928, part1("day9/example.txt"))
    }
}
