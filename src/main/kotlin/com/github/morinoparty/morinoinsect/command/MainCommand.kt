package com.github.morinoparty.morinoinsect.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.github.morinoparty.morinoinsect.catching.SpawnType
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("morinoinsect|mi|insect")
class MainCommand(
    private val insectTypeTable: InsectTypeTable,
    private val converter: InsectItemStackConverter
) : BaseCommand() {
    private val pluginName = "MorinoInsect"

    @Default
    @Subcommand("help")
    @CommandPermission("moripa.help")
    fun help(sender: CommandSender) {
        sender.sendMessage("${ChatColor.DARK_AQUA}> =====${ChatColor.AQUA}${ChatColor.BOLD}$pluginName ${ChatColor.DARK_AQUA} ===== <")
    }

    /**
     * プレイヤーに虫を与えるコマンド
     */
    @Subcommand("give")
    @CommandPermission("moripa.mod")
    @CommandCompletion("@players @insects <length>")
    fun give(sender: CommandSender, player: OnlinePlayer, insectName: String, length: Int) {
        if (sender !is Player) return

        val insect = insectTypeTable.insectMap.values.find { it.name == insectName }?.generateInsect()
            ?: return sender.sendMessage("その名前の虫は存在しません")
        val receiver = player.getPlayer()
        val insectItem = converter.createItemStack(receiver, insect)

        receiver.inventory.addItem(insectItem)
    }

    /**
     * 条件となるブロックを引数にしてランダムに虫をゲットできるデバッグ用コマンド
     */
    @Subcommand("randompickup")
    @CommandPermission("moripa.debug")
    @CommandCompletion("@blocks @spawnType")
    fun randomPickUp(sender: CommandSender, blockName: String, spawnTypeName: String) {
        if (sender !is Player) return

        val block = Material.matchMaterial(blockName)
            ?: return sender.sendMessage("そのアイテムは存在しません")
        if (!block.isBlock) return sender.sendMessage("それはブロックではありません")

        val spawnType = SpawnType.valueOf(spawnTypeName)

        val insect = insectTypeTable.pickRandomType(sender, block, spawnType)?.generateInsect()
            ?: return sender.sendMessage("このあたりに虫はいないようだ")
        val insectItem = converter.createItemStack(sender, insect)

        sender.inventory.addItem(insectItem)
    }
}
