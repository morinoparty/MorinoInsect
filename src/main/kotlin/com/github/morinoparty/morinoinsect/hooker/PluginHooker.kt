package com.github.morinoparty.morinoinsect.hooker

import com.github.morinoparty.morinoinsect.MorinoInsect
import org.bukkit.plugin.PluginManager

interface PluginHooker {
    val pluginName: String
    var hasHooked: Boolean

    fun canHook(pluginManager: PluginManager) = pluginManager.isPluginEnabled(pluginName)

    fun hook(plugin: MorinoInsect)

    fun hookIfEnabled(plugin: MorinoInsect) {
        if (canHook(plugin.server.pluginManager)) {
            hook(plugin)
        }
    }

    companion object {
        fun checkEnabled(hooker: PluginHooker, pluginManager: PluginManager) {
            check(hooker.canHook(pluginManager)) { "${hooker.pluginName} を有効にする必要があります" }
        }

        fun checkHooked(hooker: PluginHooker) {
            check(hooker.hasHooked) { "${hooker.pluginName} がフックされている必要があります" }
        }
    }
}
