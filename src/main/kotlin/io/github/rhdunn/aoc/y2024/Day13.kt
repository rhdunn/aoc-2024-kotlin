// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private data class Matrix(val a: Long, val b: Long, val c: Long, val d: Long) {
    val determinant: Long = (a*d) - (c*b)
}

private data class Button(val label: Char, val tokenCost: Int, val dx: Long, val dy: Long) {
    companion object {
        private val FORMAT = "^Button ([AB]): X\\+([0-9]+), Y\\+([0-9]+)$".toRegex()
        fun parseOrNull(input: String): Button? = FORMAT.matchEntire(input)?.let {
            val (label, dx, dy) = it.destructured
            when (label) {
                "A" -> Button('A', 3, dx.toLong(), dy.toLong())
                "B" -> Button('B', 1, dx.toLong(), dy.toLong())
                else -> null
            }
        }
    }
}

private data class Prize(val x: Long, val y: Long) {
    companion object {
        private val FORMAT = "^Prize: X=([0-9]+), Y=([0-9]+)$".toRegex()
        fun parseOrNull(input: String, offsetX: Long, offsetY: Long): Prize? = FORMAT.matchEntire(input)?.let {
            val (x, y) = it.destructured
            Prize(x.toLong() + offsetX, y.toLong() + offsetY)
        }
    }
}

private data class ClawMachine(val buttonA: Button, val buttonB: Button, val prize: Prize) {
    private val numA = Matrix(prize.x, prize.y, buttonB.dx, buttonB.dy)
    private val numB = Matrix(buttonA.dx, buttonA.dy, prize.x, prize.y)
    private val den  = Matrix(buttonA.dx, buttonA.dy, buttonB.dx, buttonB.dy)

    val a = numA.determinant.div(den.determinant)
    val b = numB.determinant.div(den.determinant)
    val tokens: Long get() = (a * buttonA.tokenCost) + (b * buttonB.tokenCost)

    private val aRemainder = numA.determinant.mod(den.determinant)
    private val bRemainder = numB.determinant.mod(den.determinant)
    val isWinnable: Boolean = aRemainder == 0L && bRemainder == 0L
}

private fun String.parseClawMachines(offsetX: Long, offsetY: Long): List<ClawMachine> {
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
            prize = Prize.parseOrNull(line, offsetX, offsetY)
        }
    }
    if (a != null && b != null && prize != null) { // No blank line at the end of the input
        machines.add(ClawMachine(a!!, b!!, prize!!))
    }
    return machines
}

object Day13 : Day<Long>(13) {
    override fun part1(data: String, args: Array<String>): Long {
        val machines = data.parseClawMachines(0, 0)
        return machines.filter { it.isWinnable }.sumOf { it.tokens }
    }

    override fun part2(data: String, args: Array<String>): Long {
        val machines = data.parseClawMachines(10000000000000, 10000000000000)
        return machines.filter { it.isWinnable }.sumOf { it.tokens }
    }
}
