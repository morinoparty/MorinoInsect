package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.Material

class MorinoInsect : KotlinPlugin() {
    init { Config.load(this) }

    val insectTypeTable = InsectTypeTable(Config.insectConfig.rarityMap, Config.insectConfig.insectMap)
    val converter = InsectItemStackConverter(this, insectTypeTable)

    override fun onPluginEnable() {

        val manager = PaperCommandManager(this)
        val completions = manager.commandCompletions
        val mainCommand = MainCommand(insectTypeTable, converter)
        manager.registerCommand(mainCommand)
        completions.registerAsyncCompletion("insects") { insectTypeTable.insectMap.values.map { it.name } }
        completions.registerAsyncCompletion("blocks") {
            Material.values().filter { it.isSolid }.map { block -> block.toString().toLowerCase() }
        }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }
}
