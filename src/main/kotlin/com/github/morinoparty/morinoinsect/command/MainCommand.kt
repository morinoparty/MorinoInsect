package com.github.morinoparty.morinoinsect.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@CommandAlias("morinoinsect|mi|insect")
class MainCommand(
    private val insectTypeTable: InsectTypeTable,
    private val converter: InsectItemStackConverter,
    private val catchingNet: ItemStack
) : BaseCommand() {
    private val pluginName = "MorinoInsect"

    @Default
    @Subcommand("help")
    @CommandPermission("moripa.help")
    fun help(sender: CommandSender) {
        sender.sendMessage("${ChatColor.DARK_AQUA}> =====${ChatColor.AQUA}${ChatColor.BOLD}$pluginName ${ChatColor.DARK_AQUA} ===== <")
    }

    @Subcommand("net")
    @CommandPermission("moripa.mod")
    fun net(sender: CommandSender) {
        if (sender !is Player) return sender.sendMessage("プレイヤーじゃねえだろ")
        sender.inventory.addItem(catchingNet)
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
    @CommandCompletion("@blocks @spawnType x y z")
    fun randomPickUp(sender: CommandSender, blockName: String, spawnTypeName: String, args: Array<String>) {
        if (sender !is Player) return

        val block = Material.matchMaterial(blockName)
            ?: return sender.sendMessage("そのアイテムは存在しません")
        if (!block.isBlock) return sender.sendMessage("それはブロックではありません")

        val spawnType = SpawnDirection.valueOf(spawnTypeName)

        // 本当はちゃんとブロックを指定しなければならないがデバッグ用なので適当な座標のブロックを取得している
        val debugLocation = Location(sender.world, args[0].toDouble(), args[1].toDouble(), args[2].toDouble())
        val debugBlock: Block = debugLocation.block

        val insect = insectTypeTable.pickRandomType(sender, debugBlock, spawnType)?.generateInsect()
            ?: return sender.sendMessage("このあたりに虫はいないようだ")
        val insectItem = converter.createItemStack(sender, insect)

        sender.inventory.addItem(insectItem)
    }
}
