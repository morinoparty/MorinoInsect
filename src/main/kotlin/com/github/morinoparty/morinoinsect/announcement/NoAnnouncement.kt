package com.github.morinoparty.morinoinsect.announcement

import org.bukkit.entity.Player

class NoAnnouncement : BaseAnnouncement {
    override fun receiversOf(catcher: Player): Collection<Player> {
        return emptyList()
    }
}
