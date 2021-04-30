package com.github.morinoparty.morinoinsect.configuration.loader

import com.github.morinoparty.morinoinsect.catching.competition.Prize

class PrizeMapLoader {
    fun loadFrom(path: Map<String, List<String>>): Map<IntRange, Prize> {
        return path.map {
            val range = intRangeFrom(it.key)
            val commands = it.value

            range to Prize(commands)
        }.toMap()
    }

    private fun intRangeFrom(string: String): IntRange {
        val tokens = string.split("~")

        val start = tokens[0].toInt()
        val end = if (tokens.size > 1) {
            if (tokens[1].isEmpty())
                Int.MAX_VALUE
            else
                tokens[1].toInt()
        } else {
            start
        }
        return IntRange(start, end)
    }
}
