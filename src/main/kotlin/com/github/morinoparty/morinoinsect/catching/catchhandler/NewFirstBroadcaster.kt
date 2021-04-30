package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.announcement.BaseAnnouncement
import com.github.morinoparty.morinoinsect.catching.competition.CatchingCompetition
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.configuration.format.TextFormat

/**
 * Created by elsiff on 2019-01-13.
 */
class NewFirstBroadcaster(
    private val competition: CatchingCompetition
) : AbstractBroadcaster() {
    override val catchMessageFormat: TextFormat
        get() = TextFormat(Config.messageConfig.get1st)

    override fun meetBroadcastCondition(insect: Insect): Boolean {
        return competition.isEnabled() && competition.willBeNewFirst(insect)
    }

    override fun announcement(insect: Insect): BaseAnnouncement {
        return Config.newFirstAnnouncement
    }
}
