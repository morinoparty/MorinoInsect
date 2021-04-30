package com.github.morinoparty.morinoinsect.catching.competition

import br.com.devsrsouza.kotlinbukkitapi.extensions.bukkit.broadcast
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.NumberUtils
import com.github.morinoparty.morinoinsect.util.TimerUtils
import com.github.morinoparty.morinoinsect.util.miniMessage
import com.github.morinoparty.morinoinsect.util.plainText
import net.kyori.adventure.text.minimessage.Template
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import kotlin.math.min

/**
 * Created by elsiff on 2019-01-18.
 */
class CatchingCompetitionHost(
    private val plugin: Plugin,
    val competition: CatchingCompetition
) {
    private val timerBarHandler: CatchingCompetitionTimerBarHandler = CatchingCompetitionTimerBarHandler(plugin)
    private var timerTask: BukkitTask? = null

    val prizes: Map<IntRange, Prize>
        get() = Config.prizeMapLoader.loadFrom(Config.standardConfig.contestPrizes)

    fun openCompetition() {
        competition.enable()

        if (Config.standardConfig.messages.broadcastStart) {
            for (player in plugin.server.onlinePlayers) {
                player.sendMessage(Config.messageConfig.contestStart.miniMessage())
            }
        }
    }

    fun openCompetitionFor(tick: Long) {
        val duration = tick / 20
        competition.enable()
        val closingWork = { closeCompetition() }
        timerTask = plugin.server.scheduler.runTaskLater(plugin, closingWork, tick)

        if (Config.standardConfig.general.useBossBar) {
            timerBarHandler.enableTimer(duration)
        }

        if (Config.standardConfig.messages.broadcastStart) {
            val msg = Config.messageConfig.contestStartTimer.miniMessage(listOf(Template.of("time", TimerUtils.time(duration))))
            for (player in plugin.server.onlinePlayers) {
                player.sendMessage(Config.messageConfig.contestStart.miniMessage())
                player.sendMessage(msg)
            }
        }
    }

    fun closeCompetition(suspend: Boolean = false) {
        competition.disable()
        if (timerTask != null) {
            timerTask?.cancel()
            timerTask = null

            if (timerBarHandler.hasTimerEnabled) {
                timerBarHandler.disableTimer()
            }
        }

        val broadcast = Config.standardConfig.messages.broadcastStop
        if (broadcast) {
            for (player in plugin.server.onlinePlayers) {
                player.sendMessage(Config.messageConfig.contestStop.miniMessage())
            }
        }
        if (!suspend) {
            if (prizes.isNotEmpty()) {
                val ranking: List<Record> = competition.ranking
                for ((range, prize) in prizes) {
                    val rangeInIndex = IntRange(
                        start = range.first - 1,
                        endInclusive = min(range.last - 1, ranking.lastIndex)
                    )
                    for (record in ranking.slice(rangeInIndex)) {
                        val rankNumber = competition.rankNumberOf(record)
                        prize.giveTo(record.insect.catcher, rankNumber, plugin)
                    }
                }
            }

            if (broadcast && Config.standardConfig.messages.showTopOnEnding) {
                for (player in plugin.server.onlinePlayers) {
                    informAboutRanking(player)
                }
            }
        }

        if (!Config.standardConfig.general.saveRecords) {
            competition.clearRecords()
        }
    }

    fun informAboutRanking(receiver: CommandSender) {
        if (competition.ranking.isEmpty()) {
            receiver.sendMessage(Config.messageConfig.topNoRecord.miniMessage())
        } else {
            val topSize = Config.standardConfig.messages.topNumber
            competition.top(topSize).forEachIndexed { index, record ->
                val number = index + 1
                val msg = Config.messageConfig.topList.miniMessage(topReplacementOf(number, record))
                receiver.sendMessage(msg)
            }

            if (receiver is Player) {
                if (!competition.containsContestant(receiver)) {
                    receiver.sendMessage(Config.messageConfig.topMineNoRecord.miniMessage())
                } else {
                    competition.rankedRecordOf(receiver).let {
                        val msg = Config.messageConfig.topMine.miniMessage(topReplacementOf(it.first, it.second))
                        receiver.sendMessage(msg)
                    }
                }
            }
        }
    }

    private fun topReplacementOf(number: Int, record: Record): List<Template> {
        return listOf(
            Template.of("ordinal", NumberUtils.ordinalOf(number)),
            Template.of("number", number.toString()),
            Template.of("player", record.insect.catcher.name),
            Template.of("length", record.insect.length.toString()),
            Template.of("insect", record.insect.type.icon.itemMeta.displayName()?.plainText() ?: record.insect.type.name)
        )
    }
}
