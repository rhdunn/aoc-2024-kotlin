// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import java.math.BigInteger

private fun String.parseArrangements(): List<BigInteger> {
    return lineSequence().filterNot { it.isEmpty() }.map { line ->
        line.split("\\s+".toRegex()).map { it.toBigInteger() }
    }.first()
}

private val BigInteger.digits: Int get() {
    var digits = 0
    var current = this
    while (current > BigInteger.ZERO) {
        ++digits
        current /= BigInteger.TEN
    }
    return digits
}

private fun BigInteger.split(n: Int): Pair<BigInteger, BigInteger> {
    var value = this
    repeat(n) { value /= BigInteger.TEN }

    var mask = value
    repeat(n) { mask *= BigInteger.TEN }

    return value to (this - mask)
}

private typealias BlinkCache = MutableMap<Pair<BigInteger, Int>, BigInteger>

private fun blink(value: BigInteger, blinks: Int, memoized: BlinkCache): BigInteger {
    val digits = value.digits
    return when {
        blinks == 0 -> memoized.getOrPut(value to 0) {
            BigInteger.ONE
        }
        value == BigInteger.ZERO -> memoized.getOrPut(value to blinks) {
            blink(BigInteger.ONE, blinks - 1, memoized)
        }
        digits % 2 == 0 -> memoized.getOrPut(value to blinks) {
            val (left, right) = value.split(digits / 2)
            blink(left, blinks - 1, memoized) + blink(right, blinks - 1, memoized)
        }
        else -> memoized.getOrPut(value to blinks) {
            blink(value * 2024.toBigInteger(), blinks - 1, memoized)
        }
    }
}

object Day11 : Day<BigInteger>(11) {
    override fun part1(data: String, args: Array<String>): BigInteger {
        val arrangements = data.parseArrangements()
        val memoized: BlinkCache = mutableMapOf()
        return arrangements.sumOf { blink(it, 25, memoized) }
    }

    override fun part2(data: String, args: Array<String>): BigInteger {
        val arrangements = data.parseArrangements()
        val memoized: BlinkCache = mutableMapOf()
        return arrangements.sumOf { blink(it, 75, memoized) }
    }
}
