package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.extensions.plugin.registerEvents
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.SpawningInsectsListener
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MorinoInsect : KotlinPlugin() {
    init { Config.load(this) }

    val insectCatchingNet = ItemStack(Config.insectConfig.insectCatchingNet.createInsectNet())
    val insectTypeTable = InsectTypeTable(Config.insectConfig.rarityMap, Config.insectConfig.insectMap)
    val converter = InsectItemStackConverter(this, insectTypeTable)

    override fun onPluginEnable() {

        server.pluginManager.apply {
            val spawningInsectsListener = SpawningInsectsListener(this@MorinoInsect, insectCatchingNet)
            registerEvents(spawningInsectsListener)
        }

        val manager = PaperCommandManager(this)
        val completions = manager.commandCompletions
        val mainCommand = MainCommand(insectTypeTable, converter, insectCatchingNet)
        manager.registerCommand(mainCommand)
        completions.registerAsyncCompletion("insects") { insectTypeTable.insectMap.values.map { it.name } }
        completions.registerAsyncCompletion("blocks") {
            Material.values().filter { it.isSolid }.map { block -> block.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("spawnTypes") {
            SpawnDirection.values().map { it.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("playerLocX") { c ->
            val player: Player = c.player
            val playerLocX = player.location.x.toInt()
            mutableListOf(playerLocX.toString())
        }
        completions.registerAsyncCompletion("playerLocY") { c ->
            val player: Player = c.player
            val playerLocY = player.location.y.toInt()
            mutableListOf(playerLocY.toString())
        }
        completions.registerAsyncCompletion("playerLocZ") { c ->
            val player: Player = c.player
            val playerLocZ = player.location.z.toInt()
            mutableListOf(playerLocZ.toString())
        }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }
}
