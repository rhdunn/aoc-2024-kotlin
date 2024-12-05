// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private enum class UpdateType(val splitChar: Char) {
    PageOrderingRule('|'),
    Pages(','),
}

private fun String.parseUpdates(): List<Pair<UpdateType, List<Int>>> {
    var state = UpdateType.PageOrderingRule
    return lineSequence().mapNotNull { line ->
        if (line.isBlank()) {
            state = UpdateType.Pages
            null
        } else {
            state to line.split(state.splitChar).map { it.toInt() }
        }
    }.toList()
}

private fun List<Int>.checkOrder(orderingRules: List<Pair<Int, Int>>): Boolean = withIndex().all { (index, page) ->
    val shouldFollow = orderingRules.filter { it.first == page }.map { it.second }
    val rest = subList(index+1, size)
    if (rest.isEmpty())
        true // only page left
    else
        rest.all { it in shouldFollow }
}

object Day05 : Day(5) {
    override fun part1(data: String): Int {
        val input = data.parseUpdates().groupBy({ it.first }, { it.second })
        val rules = input[UpdateType.PageOrderingRule]!!.map { it[0] to it[1] }
        val lists = input[UpdateType.Pages]!!
        return lists
            .filter { it.checkOrder(rules) }
            .sumOf { pages -> pages[pages.size / 2] }
    }

    override fun part2(data: String): Int {
        return 0
    }
}
