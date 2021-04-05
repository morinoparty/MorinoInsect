package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.extensions.plugin.registerEvents
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.SpawningInsectsListener
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.apache.commons.lang.Validate
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.StringUtil
import java.util.ArrayList

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
            BlockFace.values().map { it.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("playerLocX") { c ->
            val sender: CommandSender = c.sender
            Validate.notNull(sender, "Sender cannot be null")
            val senderPlayer = if (sender is Player) sender as Player else null
            val matchedPlayers = ArrayList<String>()
            for (player in Bukkit.getOnlinePlayers()) {
                val name = player.name
                val playerLocX = player.location.x.toString()
                if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(
                        name,
                        c.input
                    )
                ) {
                    matchedPlayers.add(playerLocX)
                }
            }
            matchedPlayers
        }
        completions.registerAsyncCompletion("playerLocY") { c ->
            val sender: CommandSender = c.sender
            Validate.notNull(sender, "Sender cannot be null")
            val senderPlayer = if (sender is Player) sender as Player else null
            val matchedPlayers = ArrayList<String>()
            for (player in Bukkit.getOnlinePlayers()) {
                val name = player.name
                val playerLocY = player.location.y.toString()
                if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(
                        name,
                        c.input
                    )
                ) {
                    matchedPlayers.add(playerLocY)
                }
            }
            matchedPlayers
        }
        completions.registerAsyncCompletion("playerLocZ") { c ->
            val sender: CommandSender = c.sender
            Validate.notNull(sender, "Sender cannot be null")
            val senderPlayer = if (sender is Player) sender as Player else null
            val matchedPlayers = ArrayList<String>()
            for (player in Bukkit.getOnlinePlayers()) {
                val name = player.name
                val playerLocZ = player.location.z.toString()
                if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(
                        name,
                        c.input
                    )
                ) {
                    matchedPlayers.add(playerLocZ)
                }
            }
            matchedPlayers
        }
    }

    override fun onPluginDisable() {
        // Plugin shutdown logic
    }
}
