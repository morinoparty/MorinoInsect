package com.github.morinoparty.morinoinsect.catching.listener

import br.com.devsrsouza.kotlinbukkitapi.extensions.location.dropItem
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.NetItemStackConverter
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.miniMessage
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent

class InsectDamageListener(
    private val insectConverter: InsectItemStackConverter,
    private val frameConverter: InsectItemFrameConverter,
    private val netConverter: NetItemStackConverter,
    private val globalCatchHandlers: List<CatchHandler>
) : Listener {
    @EventHandler
    fun onDamageByEntity(event: EntityDamageByEntityEvent) {
        val insectFrame = if (frameConverter.isInsectFrame(event.entity)) { // このプラグインで生成された額縁じゃなければリターン
            event.entity as ItemFrame
        } else return

        val insect = if (insectConverter.isInsect(insectFrame.item)) { // 虫じゃなければリターン
            insectConverter.insect(insectFrame.item)
        } else return

        val catcher = if (event.damager == insect.catcher) { // 虫に触れたエンティティがプレイヤーじゃなければイベントをキャンセル(干渉不可)してリターン
            event.damager as Player
        } else {
            event.isCancelled = true
            return
        }

        when (frameConverter.loadTag(insectFrame)) {
            SPAWN -> { // 自然にスポーンした虫
                if (netConverter.isInsectNet(catcher.inventory.itemInMainHand)) { // 虫あみでクリックしたら捕まえる
                    insectFrame.location.dropItem(insectFrame.item)
                    insectFrame.remove()
                    for (handler in catchHandlersOf(insect)) {
                        handler.handle(insect)
                    }
                } else { // 虫あみ以外でクリックしたら逃げる
                    insectFrame.remove()
                    catcher.sendMessage(Config.messageConfig.failureInsect.miniMessage())
                }
            }

            PLACE -> { // プレイヤーが置いた虫
                insectFrame.location.dropItem(insectFrame.item)
                insectFrame.remove()
            }
        }
    }

    @EventHandler
    fun onDamageByBlock(event: EntityDamageByBlockEvent) {
        val insectFrame = event.entity as? ItemFrame ?: return
        if (frameConverter.isInsectFrame(insectFrame)) event.isCancelled = true
    }

    private fun catchHandlersOf(insect: Insect): Collection<CatchHandler> {
        return globalCatchHandlers + insect.type.catchHandlers
    }

    companion object {
        private const val PLACE: Byte = 0
        private const val SPAWN: Byte = 1
    }
}
