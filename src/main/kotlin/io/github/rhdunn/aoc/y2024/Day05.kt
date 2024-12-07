// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day

private enum class UpdateType(val splitChar: Char) {
    PageOrderingRule('|'),
    Pages(','),
}

private fun String.parseInput(): Sequence<Pair<UpdateType, List<Int>>> {
    var state = UpdateType.PageOrderingRule
    return lineSequence().mapNotNull { line ->
        if (line.isBlank()) {
            state = UpdateType.Pages
            null
        } else {
            state to line.split(state.splitChar).map { it.toInt() }
        }
    }
}

private typealias PageOrderingRules = Map<Int, List<Int>>
private typealias PageLists = List<List<Int>>

private fun Sequence<Pair<UpdateType, List<Int>>>.toUpdates(): Pair<PageOrderingRules, PageLists> {
    val input = groupBy({ it.first }, { it.second })
    val rules = input[UpdateType.PageOrderingRule]!!.groupBy({ it[0] }, { it[1] })
    val lists = input[UpdateType.Pages]!!
    return rules to lists
}

private fun List<Int>.checkOrder(orderingRules: PageOrderingRules): Boolean = withIndex().all { (index, page) ->
    val shouldFollow = orderingRules.getOrDefault(page, listOf())
    val rest = subList(index+1, size)
    if (rest.isEmpty())
        true // only page left
    else
        rest.all { it in shouldFollow }
}

private fun List<Int>.sortByRules(orderingRules: PageOrderingRules): List<Int> = sortedWith { a, b ->
    val shouldFollowA = orderingRules.getOrDefault(a, listOf())
    when {
        a == b -> 0
        shouldFollowA.isEmpty() -> -1
        shouldFollowA.contains(b) -> 1
        else -> -1
    }
}

object Day05 : Day<Int>(5) {
    override fun part1(data: String): Int {
        val (rules, lists) = data.parseInput().toUpdates()
        return lists
            .filter { it.checkOrder(rules) }
            .sumOf { pages -> pages[pages.size / 2] }
    }

    override fun part2(data: String): Int {
        val (rules, lists) = data.parseInput().toUpdates()
        return lists
            .filterNot { it.checkOrder(rules) }
            .map { it.sortByRules(rules) }
            .sumOf { pages -> pages[pages.size / 2] }
    }
}
