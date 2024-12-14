// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import kotlin.math.pow

private fun String.parseInput(): List<Pair<Long, List<Long>>> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        val parts = line.split(":\\s+".toRegex())
        parts[0].toLong() to parts[1].split("\\s+".toRegex()).map { it.toLong() }
    }.toList()
}

private enum class Operation(val symbol: String, val apply: (Long, Long) -> Long) {
    Add("+", { a, b -> a + b}),
    Multiply("*", { a, b -> a * b }),
    Concatenate("||", { a, b -> "$a$b".toLong() });

    override fun toString(): String = symbol

    companion object {
        fun valueOf(n: Char): Operation = when (n) {
            '0' -> Add
            '1' -> Multiply
            '2' -> Concatenate
            else -> throw IllegalArgumentException("Unknown operation value $n")
        }
    }
}

private fun naryPermutations(n: Int, length: Int): Sequence<String> = sequence {
    val max = n.toDouble().pow(length.toDouble()).toInt()
    (0 until max).forEach { i ->
        yield(i.toString(n).padStart(length, '0'))
    }
}

private fun operatorCombinations(n: Int, length: Int): Sequence<List<Operation>> {
    return naryPermutations(n, length).map { permutations ->
        permutations.map { c -> Operation.valueOf(c) }
    }
}

private fun isValidInput(n: Int, total: Long, numbers: List<Long>): Boolean {
    return operatorCombinations(n, numbers.size - 1).any { operations ->
        val value = numbers.reduceIndexed { index, a, b -> operations[index - 1].apply(a, b) }
        value == total
    }
}

object Day07 : Day<Long>(7) {
    override fun part1(data: String, args: Array<String>): Long {
        val values = data.parseInput()
        val answer: Long = values
            .filter { (total, numbers) -> isValidInput(2, total, numbers) }
            .sumOf { (total, _) -> total }
        return answer
    }

    override fun part2(data: String, args: Array<String>): Long {
        val values = data.parseInput()
        val answer: Long = values
            .filter { (total, numbers) -> isValidInput(3, total, numbers) }
            .sumOf { (total, _) -> total }
        return answer
    }
}
