package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.extensions.plugin.registerEvents
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.SpawningInsectsListener
import com.github.morinoparty.morinoinsect.catching.SpawnType
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MorinoInsect : KotlinPlugin() {
    init { Config.load(this) }

    val insectCatchingNet = ItemStack(Config.insectConfig.insectCatchingNet.createInsectNet())
    val insectTypeTable = InsectTypeTable(Config.insectConfig.rarityMap, Config.insectConfig.insectMap)
    val converter = InsectItemStackConverter(this, insectTypeTable)

    override fun onPluginEnable() {

        server.pluginManager.apply {
            val spawningInsectsListener = SpawningInsectsListener(this@MorinoInsect)
            registerEvents(spawningInsectsListener)
        }

        val manager = PaperCommandManager(this)
        val completions = manager.commandCompletions
        val mainCommand = MainCommand(insectTypeTable, converter)
        manager.registerCommand(mainCommand)
        completions.registerAsyncCompletion("insects") { insectTypeTable.insectMap.values.map { it.name } }
        completions.registerAsyncCompletion("blocks") {
            Material.values().filter { it.isSolid }.map { block -> block.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("spawnType") { SpawnType.values().map { it.toString().toLowerCase() } }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }
}
