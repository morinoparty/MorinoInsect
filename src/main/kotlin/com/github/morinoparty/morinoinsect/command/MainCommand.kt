package com.github.morinoparty.morinoinsect.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.item.InsectItemStackConverter
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("morinoinsect|mi|insect")
class MainCommand(val plugin: MorinoInsect) : BaseCommand() {

    private val pluginName = "MorinoInsect"
    private val converter = InsectItemStackConverter(plugin, plugin.insectTypeTable)

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
    @CommandCompletion("@players @insect <length>")
    fun give(sender: CommandSender, player: OnlinePlayer, insectName: String, length: Int) {
        if (sender !is Player) return
        val insectType = plugin.insectTypeTable.insectMap.values.find { it.name == insectName }
            ?: return sender.sendMessage("その名前の虫は存在しません")
        val receiver = player.getPlayer()

        receiver.inventory.addItem(converter.createItemStack(receiver, Insect(insectType, length)))
    }
}
