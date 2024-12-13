// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import kotlin.math.abs
import kotlin.math.roundToLong

private data class Button(val label: Char, val tokenCost: Int, val dx: Int, val dy: Int) {
    companion object {
        private val FORMAT = "^Button ([AB]): X\\+([0-9]+), Y\\+([0-9]+)$".toRegex()
        fun parseOrNull(input: String): Button? = FORMAT.matchEntire(input)?.let {
            val (label, dx, dy) = it.destructured
            when (label) {
                "A" -> Button('A', 3, dx.toInt(), dy.toInt())
                "B" -> Button('B', 1, dx.toInt(), dy.toInt())
                else -> null
            }
        }
    }
}

private data class Prize(val x: Long, val y: Long) {
    companion object {
        private val FORMAT = "^Prize: X=([0-9]+), Y=([0-9]+)$".toRegex()
        fun parseOrNull(input: String): Prize? = FORMAT.matchEntire(input)?.let {
            val (x, y) = it.destructured
            Prize(x.toLong(), y.toLong())
        }
    }
}

private data class ClawMachine(val buttonA: Button, val buttonB: Button, val prize: Prize) {
    // m is the constant terms
    // n is the terms that are factors of the variable

    // solve for a
    val aM: Double = prize.x.toDouble() / buttonA.dx
    val aN: Double = buttonB.dx.toDouble() / buttonA.dx
    fun a(b: Long): Long = (aM - (aN * b)).roundToLong()

    // solve for b
    val bM: Double = prize.y.toDouble() / buttonB.dy
    val bN: Double = buttonA.dy.toDouble() / buttonB.dy
    fun b(a: Long): Long = (bM - (bN * a)).roundToLong()

    // solve for a after substituting b in terms of a
    val cM: Double = aM - (aN * bM)
    val cN: Double = 1 - (aN * bN)
    val c: Double = (cM / cN)

    val a: Long = c.roundToLong()
    val b: Long = b(a)

    val isWinnable: Boolean = abs(c - a) < 0.00000001

    val tokens: Long get() = (a * buttonA.tokenCost) + (b * buttonB.tokenCost)
}

private fun String.parseClawMachines(): List<ClawMachine> {
    val machines = mutableListOf<ClawMachine>()
    var a: Button? = null
    var b: Button? = null
    var prize: Prize? = null
    lineSequence().forEach { line ->
        if (line.isEmpty()) {
            if (a != null && b != null && prize != null) {
                machines.add(ClawMachine(a!!, b!!, prize!!))
            }
            a = null
            b = null
            prize = null
        } else if (line.startsWith("Button A: ")) {
            a = Button.parseOrNull(line)
        } else if (line.startsWith("Button B: ")) {
            b = Button.parseOrNull(line)
        } else if (line.startsWith("Prize: ")) {
            prize = Prize.parseOrNull(line)
        }
    }
    if (a != null && b != null && prize != null) { // No blank line at the end of the input
        machines.add(ClawMachine(a!!, b!!, prize!!))
    }
    return machines
}

object Day13 : Day<Long>(13) {
    override fun part1(data: String): Long {
        val machines = data.parseClawMachines()
        return machines.filter { it.isWinnable }.sumOf { it.tokens }
    }

    override fun part2(data: String): Long {
        return 0
    }
}
