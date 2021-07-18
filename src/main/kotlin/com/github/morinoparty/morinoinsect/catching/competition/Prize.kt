package com.github.morinoparty.morinoinsect.catching.competition

import com.github.morinoparty.morinoinsect.util.NumberUtils
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * Created by elsiff on 2019-01-20.
 */
class Prize(
    private val commands: List<String>
) {
    fun giveTo(player: Player, rankNumber: Int, plugin: Plugin) {
        if (!player.isOnline) {
            val ordinal = NumberUtils.ordinalOf(rankNumber)
            plugin.logger.warning("$ordinal catcher ${player.name} isn't online! Contest prizes may not be sent.")
        }

        val server = plugin.server
        player.name.let {
            for (command in commands) {
                server.dispatchCommand(server.consoleSender, command.replace("<player>", it))
            }
        }
    }
}
