package com.github.morinoparty.morinoinsect.catching.listener

import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.miniMessage
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSprintEvent

class SearchingWatcher(
    private val insectConverter: InsectItemStackConverter,
    private val frameConverter: InsectItemFrameConverter
) : Listener {
    @EventHandler
    fun onToggleSprint(event: PlayerToggleSprintEvent) {
        val nearbyInsectFrames = if (event.isSprinting) {
            event.player.location.getNearbyEntitiesByType(ItemFrame::class.java, RADIUS)
        } else return

        for (itemFrame in nearbyInsectFrames) {
            if (frameConverter.isInsectFrame(itemFrame) &&
                frameConverter.loadTag(itemFrame) == SPAWN &&
                insectConverter.insect(itemFrame.item).catcher == event.player
            ) {
                itemFrame.remove()
                event.player.sendMessage(Config.messageConfig.failureInsect.miniMessage())
            }
        }
    }

    companion object {
        private const val RADIUS = 16.0
        private const val SPAWN: Byte = 1
    }
}
