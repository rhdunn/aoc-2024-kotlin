// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.run
import io.github.rhdunn.aoc.y2024.*

val days = listOf<Day<*>>(
    Day01, Day02, Day03, Day04, Day05,
    Day06, Day07, Day08, Day09, Day10,
    Day11, Day12, Day13, Day14,
)

fun main(args: Array<String>) {
    println("Advent of Code 2024 Kotlin")

    val task = args.getOrNull(0)
    val fileName = args.getOrNull(1)
    if (task == null || fileName == null) {
        println("usage: aoc-2024-kotlin DAY-PART FILE")
        return
    }

    val loader = Day::class.java.classLoader
    val data = loader.getResourceAsStream(fileName)?.bufferedReader()?.use { it.readText() }
    if (data == null) {
        println("cannot find file: $fileName")
        return
    }

    days.run(task, data, args.sliceArray(2 until args.size))
}
