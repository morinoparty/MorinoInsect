package com.github.morinoparty.morinoinsect.configuration.format

import com.github.morinoparty.morinoinsect.hooker.PlaceholderApiHooker
import org.bukkit.entity.Player

interface Format<T> {
    fun replace(player: Player): T

    companion object {
        private lateinit var placeholderApiHooker: PlaceholderApiHooker

        fun init(placeholderApiHooker: PlaceholderApiHooker) {
            this.placeholderApiHooker = placeholderApiHooker
        }

        fun tryReplacing(string: String, player: Player): String {
            return if (::placeholderApiHooker.isInitialized && placeholderApiHooker.hasHooked)
                placeholderApiHooker.tryReplacing(string, player)
            else
                string
        }
    }
}
