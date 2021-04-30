package com.github.morinoparty.morinoinsect.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.competition.CatchingCompetitionHost
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.NetItemStackConverter
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.TimerUtils
import com.github.morinoparty.morinoinsect.util.miniMessage
import net.kyori.adventure.text.minimessage.Template
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("morinoinsect|mi|insect")
class MainCommand(
    private val morinoInsect: MorinoInsect,
    private val insectTypeTable: InsectTypeTable,
    private val insectConverter: InsectItemStackConverter,
    private val netConverter: NetItemStackConverter,
    private val competitionHost: CatchingCompetitionHost
) : BaseCommand() {
    private val pluginName = "MorinoInsect"
    private val competition = competitionHost.competition

    @Default
    @Subcommand("help")
    @CommandPermission("moripa.help")
    fun help(sender: CommandSender) {
        sender.sendMessage("${ChatColor.DARK_AQUA}> =====${ChatColor.AQUA}${ChatColor.BOLD}$pluginName ${ChatColor.DARK_AQUA} ===== <")
    }

    @Subcommand("reload")
    @CommandPermission("moripa.mod")
    fun reload(sender: CommandSender) {
        try {
            morinoInsect.applyConfig()
            sender.sendMessage("リロード完了")
        } catch (e: Exception) {
            e.printStackTrace()
            sender.sendMessage("リロード失敗")
        }
    }

    @Subcommand("begin|start")
    @CommandPermission("moripa.mod")
    fun begin(sender: CommandSender, runningTime: Long) {
        if (!competition.isEnabled()) {
            competitionHost.openCompetitionFor(runningTime * 20)

            if (!Config.standardConfig.messages.broadcastStart) {
                val msg = Config.messageConfig.contestStartTimer.miniMessage(listOf(Template.of("time", TimerUtils.time(runningTime))))
                sender.sendMessage(msg)
            }
        } else {
            sender.sendMessage("開催できん")
        }
    }

    @Subcommand("suspend")
    @CommandPermission("moripa.mod")
    fun suspend(sender: CommandSender) {
        if (!competition.isDisabled()) {
            competitionHost.closeCompetition(suspend = true)

            if (!Config.standardConfig.messages.broadcastStop) {
                sender.sendMessage(Config.messageConfig.contestStop)
            }
        } else {
            sender.sendMessage("一時停止")
        }
    }

    @Subcommand("end")
    @CommandPermission("moripa.mod")
    fun end(sender: CommandSender) {
        if (!competition.isDisabled()) {
            competitionHost.closeCompetition()

            if (!Config.standardConfig.messages.broadcastStop) {
                sender.sendMessage(Config.messageConfig.contestStop)
            }
        } else {
            sender.sendMessage("already-stopped")
        }
    }

    @Subcommand("top|ranking")
    @CommandPermission("default")
    fun top(sender: CommandSender) {
        competitionHost.informAboutRanking(sender)
    }

    // デバッグ用コマンド群
    // だおｇｈなｒｇばぽｇばいｗｂｇ

    @Subcommand("net")
    @CommandPermission("moripa.mod")
    fun net(sender: CommandSender) {
        if (sender !is Player) return sender.sendMessage("プレイヤーじゃねえだろ")
        sender.inventory.addItem(netConverter.createInsectNet(Config.customItemStackLoader.loadFrom(Config.insectConfig.insectNet)))
    }

    /**
     * プレイヤーに虫を与えるコマンド
     */
    @Subcommand("give")
    @CommandPermission("moripa.mod")
    @CommandCompletion("@players @insects <length>")
    fun give(sender: CommandSender, player: OnlinePlayer, insectName: String, length: Double) {
        val insectType = insectTypeTable.types.find { it.name == insectName }
            ?: return sender.sendMessage("その名前の虫は存在しません")
        val insect = Insect(insectType, length, player.player)
        val insectItem = insectConverter.createItemStack(insect)

        player.player.inventory.addItem(insectItem)
    }

    /**
     * 条件となるブロックを引数にしてランダムに虫をゲットできるデバッグ用コマンド
     */
    @Subcommand("spawn")
    @CommandPermission("moripa.mod")
    @CommandCompletion("@spawnTypes @playerLocX @playerLocY @playerLocZ")
    fun randomPickUp(sender: CommandSender, face: String, args: Array<String>) {
        if (sender !is Player) return

        val direction = SpawnDirection.valueOf(face.toUpperCase())
        val location = Location(sender.world, args[0].toDouble(), args[1].toDouble(), args[2].toDouble())
        val block: Block = location.block

        val insect = insectTypeTable.pickRandomType(catcher = sender, material = block.type, spawnDirection = direction)?.generateInsect(sender)
            ?: return sender.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
        val insectItem = insectConverter.createItemStack(insect)

        sender.inventory.addItem(insectItem)
    }
}
