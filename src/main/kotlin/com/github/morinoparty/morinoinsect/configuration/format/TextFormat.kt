package com.github.morinoparty.morinoinsect.configuration.format

import org.bukkit.entity.Player

class TextFormat(
    private var string: String
) : Format<String> {
    override fun replace(player: Player): String {
        return Format.tryReplacing(string, player)
    }
}
