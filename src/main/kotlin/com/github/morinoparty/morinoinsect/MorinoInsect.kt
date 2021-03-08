package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.command.MainCommand

class MorinoInsect : KotlinPlugin() {
    val morinoInsect = this

    override fun onPluginEnable() {
        // Plugin startup logic
        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        manager.registerCommand(mainCommand)
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }
}
