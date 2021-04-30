package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.catching.competition.CatchingCompetition
import com.github.morinoparty.morinoinsect.catching.competition.Record
import com.github.morinoparty.morinoinsect.catching.insect.Insect

class CompetitionRecordAdder(
    private val competition: CatchingCompetition
) : CatchHandler {
    override fun handle(insect: Insect) {
        if (competition.isEnabled()) {
            competition.putRecord(Record(insect))
        }
    }
}
