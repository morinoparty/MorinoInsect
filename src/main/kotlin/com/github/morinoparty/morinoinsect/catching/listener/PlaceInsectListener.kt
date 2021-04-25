package com.github.morinoparty.morinoinsect.catching.listener

import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.miniMessage
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PlaceInsectListener(
    private val insectConverter: InsectItemStackConverter,
    private val frameConverter: InsectItemFrameConverter
) : Listener {
    @EventHandler
    fun onRightClick(event: PlayerInteractEvent) {
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) &&
            event.hasBlock()
        ) {
            val item = event.item?.asOne() ?: return
            val block = event.clickedBlock ?: return

            val itemFrames: List<ItemFrame> = block.chunk.entities.filter {
                it is ItemFrame
            } as? List<ItemFrame> ?: throw IllegalStateException("予期せぬ不具合が発生しました")
            if (itemFrames.size >= 16) return event.player.sendMessage(Config.messageConfig.limitOver.miniMessage())
            if (insectConverter.isInsect(item)) {
                try {
                    frameConverter.place(block, item, event.blockFace).also {
                        event.player.inventory.removeItem(item)
                    }
                } catch (e: IllegalArgumentException) {
                    return
                }
            }
        }
    }
}
