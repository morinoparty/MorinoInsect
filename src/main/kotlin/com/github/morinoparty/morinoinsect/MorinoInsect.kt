package com.github.morinoparty.morinoinsect

import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.command.MainCommand
import org.bukkit.plugin.java.JavaPlugin

class MorinoInsect : JavaPlugin() {
    val morinoInsect = this

    override fun onEnable() {
        // Plugin startup logic
        val manager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        manager.registerCommand(mainCommand)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
