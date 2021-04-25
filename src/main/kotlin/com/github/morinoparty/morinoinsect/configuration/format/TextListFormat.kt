package com.github.morinoparty.morinoinsect.configuration.format

import org.bukkit.entity.Player

class TextListFormat(
    private var strings: List<String>
) : Format<List<String>> {
    override fun replace(player: Player): List<String> {
        return strings.map { Format.tryReplacing(it, player) }
    }
}
