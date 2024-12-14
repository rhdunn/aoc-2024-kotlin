// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private fun String.parseMuls(): List<Pair<Int, Int>> {
    return """mul\((\d+),(\d+)\)""".toRegex().findAll(this).map { match ->
        match.groupValues[1].toInt() to match.groupValues[2].toInt()
    }.toList()
}

private sealed interface Command
private data class Mul(val a: Int, val b: Int) : Command // mul(a,b)
private object EnableMul : Command // do()
private object DisableMul : Command // don't()

private fun String.parseCommands(): List<Command> {
    return """((mul)\((\d+),(\d+)\)|(do(n't)?)\(\))""".toRegex().findAll(this).mapNotNull { match ->
        val command = match.groupValues[2].takeIf { it.isNotEmpty() } ?: match.groupValues[5]
        when (command) {
            "mul" -> Mul(match.groupValues[3].toInt(), match.groupValues[4].toInt())
            "do" -> EnableMul
            "don't" -> DisableMul
            else -> null
        }
    }.toList()
}

object Day03 : Day<Int>(3) {
    override fun part1(data: String, args: Array<String>): Int {
        return data.parseMuls().sumOf { (a, b) -> a * b }
    }

    override fun part2(data: String, args: Array<String>): Int {
        var enabled = true
        val commands = data.parseCommands().mapNotNull { c ->
            when (c) {
                is Mul -> c.takeIf { enabled }
                is EnableMul -> { enabled = true; null }
                is DisableMul -> { enabled = false; null }
            }
        }
        return commands.sumOf { (a, b) -> a * b }
    }
}
