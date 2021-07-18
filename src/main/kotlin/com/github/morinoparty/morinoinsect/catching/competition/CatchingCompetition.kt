package com.github.morinoparty.morinoinsect.catching.competition

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.dao.DaoFactory
import com.github.morinoparty.morinoinsect.dao.RecordDao
import org.bukkit.entity.Player

/**
 * Created by elsiff on 2018-12-25.
 */
class CatchingCompetition {
    var state: State = State.DISABLED

    val ranking: List<Record>
        get() = records.all()

    private val records: RecordDao
        get() = DaoFactory.records

    fun enable() {
        checkStateDisabled()

        state = State.ENABLED
    }

    fun disable() {
        checkStateEnabled()

        state = State.DISABLED
    }

    fun isEnabled(): Boolean =
        state == State.ENABLED

    fun isDisabled(): Boolean =
        state == State.DISABLED

    fun willBeNewFirst(insect: Insect): Boolean {
        return ranking.isEmpty() || ranking.first().let { insect.length > it.insect.length && it.insect.catcher != insect.catcher }
    }

    fun putRecord(record: Record) {
        checkStateEnabled()

        if (containsContestant(record.insect.catcher)) {
            val oldRecord = recordOf(record.insect.catcher)
            if (record.insect.length > oldRecord.insect.length) {
                records.update(record)
            }
        } else {
            records.insert(record)
        }
    }

    fun containsContestant(contestant: Player): Boolean =
        ranking.any { it.insect.catcher == contestant }

    fun rankNumberOf(record: Record): Int =
        ranking.indexOf(record) + 1

    fun recordOf(contestant: Player): Record {
        for (record in ranking) {
            if (record.insect.catcher == contestant) {
                return record
            }
        }
        throw IllegalStateException("Record not found")
    }

    fun rankedRecordOf(contestant: Player): Pair<Int, Record> {
        for ((index, record) in ranking.withIndex()) {
            if (record.insect.catcher == contestant) {
                return Pair(index + 1, record)
            }
        }
        throw IllegalStateException("Record not found")
    }

    fun recordOf(rankNumber: Int): Record {
        require(rankNumber >= 1 && rankNumber <= ranking.size) { "Rank number is out of records size" }
        return ranking.elementAt(rankNumber - 1)
    }

    fun top(size: Int): List<Record> =
        records.top(size)

    fun clearRecords() =
        records.clear()

    private fun checkStateEnabled() =
        check(state == State.ENABLED) { "Fishing competition hasn't enabled" }

    private fun checkStateDisabled() =
        check(state == State.DISABLED) { "Fishing competition hasn't disabled" }

    enum class State { ENABLED, DISABLED }
}
