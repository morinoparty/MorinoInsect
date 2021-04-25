package com.github.morinoparty.morinoinsect.configuration.loader

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

class TextColorLoader {
    fun loadFrom(string: String): TextColor {
        return TextColor.fromCSSHexString(string)
            ?: return TextColor.fromHexString(string)
                ?: return NamedTextColor.NAMES.value(string)
                    ?: return NamedTextColor.WHITE
    }
}
