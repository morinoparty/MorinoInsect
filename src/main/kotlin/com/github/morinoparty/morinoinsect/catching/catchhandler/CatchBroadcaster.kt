package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.announcement.BaseAnnouncement
import com.github.morinoparty.morinoinsect.announcement.NoAnnouncement
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.configuration.format.TextFormat

class CatchBroadcaster : AbstractBroadcaster() {
    override val catchMessageFormat: TextFormat
        get() = TextFormat(Config.messageConfig.catchInsect)

    override fun meetBroadcastCondition(insect: Insect): Boolean {
        return insect.type.rarity.catchAnnouncement !is NoAnnouncement
    }

    override fun announcement(insect: Insect): BaseAnnouncement {
        return insect.type.rarity.catchAnnouncement
    }
}
