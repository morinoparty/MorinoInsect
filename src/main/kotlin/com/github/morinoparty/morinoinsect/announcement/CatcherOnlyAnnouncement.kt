package com.github.morinoparty.morinoinsect.announcement

import org.bukkit.entity.Player

class CatcherOnlyAnnouncement : BaseAnnouncement {
    override fun receiversOf(catcher: Player): Collection<Player> {
        return setOf(catcher)
    }
}
