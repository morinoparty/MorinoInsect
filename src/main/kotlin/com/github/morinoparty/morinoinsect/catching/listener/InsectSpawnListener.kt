package com.github.morinoparty.morinoinsect.catching.listener

import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.miniMessage
import com.okkero.skedule.schedule
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

class InsectSpawnListener(
    private val plugin: Plugin,
    private val scheduler: BukkitScheduler,
    private val insectConverter: InsectItemStackConverter,
    private val frameConverter: InsectItemFrameConverter
) : Listener {
    @EventHandler
    fun onSpawn(event: EntitySpawnEvent) {
        scheduler.schedule(plugin) {
            val itemFrame = event.entity as? ItemFrame
                ?: return@schedule
            val insect = if (
                frameConverter.isInsectFrame(itemFrame) &&
                frameConverter.loadTag(itemFrame) == SPAWN
            ) {
                insectConverter.insect(itemFrame.item)
            } else return@schedule
            insect.catcher.sendMessage(Config.messageConfig.spawnInsect.miniMessage())
            insect.catcher.playSound(Sound.sound(Key.key("morinoparty.spawn"), Sound.Source.AMBIENT, 1.0f, 1.0f))

            waitFor(ONE_SECOND * Config.standardConfig.general.failureTime)

            if (!itemFrame.isDead) {
                insect.catcher.sendMessage(Config.messageConfig.failureInsect.miniMessage())
                itemFrame.remove()
            }
        }
    }

    companion object {
        private const val ONE_SECOND: Long = 20
        private const val SPAWN: Byte = 1
    }
}
