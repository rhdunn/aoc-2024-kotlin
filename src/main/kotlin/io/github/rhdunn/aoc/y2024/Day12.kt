// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import io.github.rhdunn.aoc.Direction
import io.github.rhdunn.aoc.Grid

private data class Plot(val plant: Char) {
    var walls = mutableSetOf(Direction.Up, Direction.Right, Direction.Down, Direction.Left)

    override fun toString() = plant.toString()
}

private data class PlotReference(val x: Int, val y: Int, val plot: Plot) {
    val plant by plot::plant
    val walls by plot::walls

    override fun toString() = "($x, $y)"
}

private data class Region(val plots: MutableSet<PlotReference> = mutableSetOf()) {
    val area: Int get() = plots.size
    val perimeter: Int get() = plots.sumOf { it.walls.size }

    override fun toString() = "${plots.first().plant} of $plots"
}

private fun Grid<Plot>.plots(): Sequence<PlotReference> = sequence {
    indices.forEach { (x, y) -> yield(PlotReference(x, y, this@plots[x, y])) }
}

private fun Grid<Plot>.adjacentPlots(
    x: Int,
    y: Int
): Sequence<Pair<Direction, PlotReference>> = sequence {
    Direction.entries.forEach { wall ->
        val adjacentX = x + wall.dx
        val adjacentY = y + wall.dy
        if (adjacentX in widths && adjacentY in heights) {
            val adjacent = this@adjacentPlots[adjacentX, adjacentY]
            yield(wall to PlotReference(adjacentX, adjacentY, adjacent))
        }
    }
}

private fun Grid<Plot>.mergeAdjacentWalls() {
    plots().forEach { plot ->
        adjacentPlots(plot.x, plot.y)
            .filter { (_, adjacent) -> plot.plant == adjacent.plant }
            .forEach { (wall, adjacent) -> // Remove the wall between adjacent plots
                plot.walls.remove(wall)
                adjacent.walls.remove(wall.opposite)
            }
    }
}

private fun Grid<Plot>.fillRegion(plot: PlotReference): Region {
    val region = Region()
    val plots = mutableSetOf(plot)
    while (plots.isNotEmpty()) {
        val current = plots.first()
        plots.remove(current)
        region.plots.add(current)

        adjacentPlots(current.x, current.y)
            // Only check adjacent plots with the same plant type
            .filter { (_, adjacent) -> current.plant == adjacent.plant }
            // Don't check plots that have already been visited
            .filterNot { (_, adjacent) -> region.plots.contains(adjacent) }
            // Push the plot onto the queue to visit
            .forEach { (_, adjacent) -> plots.add(adjacent) }
    }
    return region
}

private fun Grid<Plot>.regions(): List<Region> {
    val regions = mutableListOf<Region>()
    val visited = mutableSetOf<PlotReference>()
    plots().forEach { plot ->
        if (!visited.contains(plot)) {
            val region = fillRegion(plot)
            visited.addAll(region.plots)
            regions.add(region)
        }
    }
    return regions
}

object Day12 : Day<Int>(12) {
    override fun part1(data: String): Int {
        val garden = Grid.parse(data) { Plot(it) }
        garden.mergeAdjacentWalls()
        return garden.regions().sumOf { it.area * it.perimeter }
    }

    override fun part2(data: String): Int {
        return 0
    }
}
