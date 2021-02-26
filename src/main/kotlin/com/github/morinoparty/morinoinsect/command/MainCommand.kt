package com.github.morinoparty.morinoinsect.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.github.morinoparty.morinoinsect.MorinoInsect
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("morinoinsect|mi|insect")
class MainCommand(val plugin: MorinoInsect): BaseCommand() {

    val pluginName = "MorinoInsect"
    val prefix = "${ChatColor.AQUA}[$pluginName]${ChatColor.RESET} "

    @Default
    @Subcommand("help")
    @CommandPermission("moripa.help")
    fun help (sender: CommandSender) {
        sender.sendMessage("$prefix ${ChatColor.DARK_AQUA}> =====${ChatColor.AQUA}${ChatColor.BOLD}$pluginName ${ChatColor.DARK_AQUA} ===== <")
    }

}