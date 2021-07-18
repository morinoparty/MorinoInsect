package com.github.morinoparty.morinoinsect.catching.competition

import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.TimerUtils
import com.github.morinoparty.morinoinsect.util.miniMessage
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Template
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * Created by elsiff on 2019-01-19.
 */
class CatchingCompetitionTimerBarHandler(
    private val plugin: Plugin
) {
    private var timerBar: BossBar? = null
    private var barUpdatingTask: BukkitTask? = null
    private var barDisplayer: TimerBarDisplayer? = null

    val hasTimerEnabled: Boolean
        get() = timerBar != null

    fun enableTimer(duration: Long) {
        val name = Component.text("Foo Bar")
        val barColor = BossBar.Color.valueOf(Config.standardConfig.messages.contestBarColor.toUpperCase())
        timerBar = BossBar.bossBar(name, 1F, barColor, BossBar.Overlay.NOTCHED_10)
        timerBar?.let { Bukkit.getServer().showBossBar(it) }

        barUpdatingTask = TimerBarUpdater(duration).runTaskTimer(plugin, 0, 20L)
        barDisplayer = TimerBarDisplayer().also {
            plugin.server.pluginManager.registerEvents(it, plugin)
        }
    }

    fun disableTimer() {
        barUpdatingTask?.cancel()
        barUpdatingTask = null

        timerBar?.apply {
            this.name(timerBarTitle(0))
            this.progress(0.0F)
        }

        barDisplayer?.let { HandlerList.unregisterAll(it) }
        barDisplayer = null

        timerBar?.let { Bukkit.getServer().hideBossBar(it) }
        timerBar = null
    }

    private fun timerBarTitle(remainingSeconds: Long): Component =
        Config.messageConfig.timerBossBar.miniMessage(
            listOf(Template.of("time", TimerUtils.time(remainingSeconds)))
        )

    private inner class TimerBarUpdater(
        private val duration: Long
    ) : BukkitRunnable() {
        private var remainingSeconds: Long = duration

        override fun run() {
            remainingSeconds--
            timerBar?.run {
                this.name(timerBarTitle(remainingSeconds))
                this.progress(remainingSeconds.toFloat() / duration)
            }
        }
    }

    private inner class TimerBarDisplayer : Listener {
        @EventHandler
        fun onPlayerJoin(event: PlayerJoinEvent) =
            timerBar?.let { event.player.showBossBar(it) }

        @EventHandler
        fun onPlayerQuit(event: PlayerQuitEvent) =
            timerBar?.let { event.player.hideBossBar(it) }
    }
}
