package com.github.morinoparty.morinoinsect.announcement

import org.bukkit.entity.Player

class RangedAnnouncement(
    private val radius: Double
) : BaseAnnouncement {
    override fun receiversOf(catcher: Player): Collection<Player> {
        return catcher.world.players.filter {
            it.location.distance(catcher.location) <= radius
        }
    }
}
