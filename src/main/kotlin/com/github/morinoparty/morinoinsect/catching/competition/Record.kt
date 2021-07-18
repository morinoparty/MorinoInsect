package com.github.morinoparty.morinoinsect.catching.competition

import com.github.morinoparty.morinoinsect.catching.insect.Insect

/**
 * Created by elsiff on 2018-12-25.
 */
data class Record(val insect: Insect) : Comparable<Record> {
    override fun compareTo(other: Record): Int {
        return insect.length.compareTo(other.insect.length)
    }
}
