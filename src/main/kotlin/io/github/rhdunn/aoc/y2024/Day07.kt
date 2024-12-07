// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private fun String.parseInput(): List<Pair<Long, List<Long>>> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        val parts = line.split(":\\s+".toRegex())
        parts[0].toLong() to parts[1].split("\\s+".toRegex()).map { it.toLong() }
    }.toList()
}

private enum class Operation(val symbol: Char, val apply: (Long, Long) -> Long) {
    Add('+', { a, b -> a + b}),
    Multiply('*', { a, b -> a * b });

    override fun toString(): String = symbol.toString()
}

// As there are only 2 operations we can count from 0 to 2^n and use the binary:
// - 0 = add
// - 1 = multiply
private fun operatorCombinations(length: Int): Sequence<List<Operation>> = sequence {
    val max = 1 shl length
    (0 .. max).forEach { n ->
        val operations = MutableList(length) { i ->
            if ((n and (1 shl i)) == 0)
                Operation.Add
            else
                Operation.Multiply
        }
        yield(operations)
    }
}

private fun isValidInput(total: Long, numbers: List<Long>): Boolean {
    return operatorCombinations(numbers.size - 1).any { operations ->
        val value = numbers.reduceIndexed { index, a, b -> operations[index - 1].apply(a, b) }
        value == total
    }
}

object Day07 : Day<Long>(7) {
    override fun part1(data: String): Long {
        val values = data.parseInput()
        val answer: Long = values
            .filter { (total, numbers) -> isValidInput(total, numbers) }
            .sumOf { (total, _) -> total }
        return answer
    }

    override fun part2(data: String): Long {
        return 0
    }
}
