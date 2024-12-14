// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.tests.y2024

import io.github.rhdunn.aoc.tests.DayTest
import io.github.rhdunn.aoc.y2024.Day14
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Tests : DayTest<Int>(Day14) {
    @Test
    fun part1() {
        assertEquals(12, part1("day14/example.txt", arrayOf("11", "7")))
    }
}
