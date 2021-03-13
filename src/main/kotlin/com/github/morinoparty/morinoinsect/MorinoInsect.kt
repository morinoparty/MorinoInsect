package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.command.MainCommand

class MorinoInsect : KotlinPlugin() {

    override fun onPluginEnable() {
        // Plugin startup logic
        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        instance = this
        manager.registerCommand(mainCommand)
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }

    companion object {

        @JvmStatic
        private lateinit var instance: MorinoInsect

        @JvmStatic
        fun getInstance(): MorinoInsect {
            return instance
        }
    }
}
