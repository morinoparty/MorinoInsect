package com.github.morinoparty.morinoinsect.announcement

import org.bukkit.entity.Player

class ServerAnnouncement : BaseAnnouncement {
    override fun receiversOf(catcher: Player): Collection<Player> {
        return catcher.server.onlinePlayers
    }
}
