// Copyright (C) 2024 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.aoc.y2024

import io.github.rhdunn.aoc.Day
import kotlin.math.min

private data class DiskMapEntry(val id: Int, val length: Int) {
    override fun toString(): String = when (id) {
        FreeSpace -> ".{$length}"
        else -> "$id{$length}"
    }

    companion object {
        const val FreeSpace = -1
    }
}

private fun String.parseDiskMap(): Sequence<DiskMapEntry> = sequence {
    var id = 0
    var isFile = true
    filter { it.isDigit() }.forEach { c ->
        val length = c - '0'
        if (isFile) {
            yield(DiskMapEntry(id, length))
            ++id
            isFile = false
        } else {
            yield(DiskMapEntry(DiskMapEntry.FreeSpace, length))
            isFile = true
        }
    }
}

private data class DiskFilePointer(
    var index: Int,
    val increment: Int,
    var capacity: Int = 0,
) : Comparable<DiskFilePointer> {
    override fun toString(): String = "$index{$capacity}"

    override fun compareTo(other: DiskFilePointer): Int = index.compareTo(other.index)

    fun advance(disk: List<DiskMapEntry>) {
        index += increment
        capacity = disk.getOrNull(index)?.length ?: 0
    }
}

private fun List<DiskMapEntry>.pack(): Sequence<DiskMapEntry> = sequence {
    var read = DiskFilePointer(0, 1)
    val write = DiskFilePointer(size, -1)

    while (read < write) {
        val r = get(read.index)
        val w = getOrNull(write.index)

        if (r.id != DiskMapEntry.FreeSpace) { // Return the file
            yield(r)
            read.advance(this@pack)
            continue
        }

        if (read.capacity == 0) { // Out of space
            read.advance(this@pack)
            continue
        }

        if (w?.id == DiskMapEntry.FreeSpace) { // Unallocated block
            write.advance(this@pack)
            continue
        }

        if (write.capacity == 0) { // Written all of the file to disk
            write.advance(this@pack)
            continue
        }

        val next = DiskMapEntry(w!!.id, min(read.capacity, write.capacity))
        yield(next)

        read.capacity -= next.length
        write.capacity -= next.length
    }

    // Write any remaining content of the last file
    if (write.capacity != 0) {
        val w = get(write.index)
        yield(DiskMapEntry(w.id, min(read.capacity, write.capacity)))
    }

    // Write all the free space entries at the end
    read = DiskFilePointer(0, 1)
    while (read.index < size) {
        val r = get(read.index)
        if (r.id == DiskMapEntry.FreeSpace) {
            yield(r)
        }
        read.advance(this@pack)
    }
}

private fun Sequence<DiskMapEntry>.checksums(): Sequence<Long> = sequence {
    var i = 0
    forEach { entry ->
        if (entry.id == DiskMapEntry.FreeSpace) return@forEach

        (0 until entry.length).forEach { n ->
            yield(entry.id.toLong() * i)
            ++i
        }
    }
}

object Day09 : Day<Long>(9) {
    override fun part1(data: String): Long {
        val disk = data.parseDiskMap().toList()
        return disk.pack().checksums().sum()
    }

    override fun part2(data: String): Long {
        return 0
    }
}
