package com.github.morinoparty.morinoinsect.dao

import com.github.morinoparty.morinoinsect.catching.competition.Record

interface RecordDao {
    fun insert(record: Record)

    fun update(record: Record)

    fun clear()

    fun top(size: Int): List<Record>

    fun all(): List<Record>
}
