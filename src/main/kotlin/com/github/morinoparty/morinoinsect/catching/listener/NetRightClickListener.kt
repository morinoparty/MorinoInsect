package com.github.morinoparty.morinoinsect.catching.listener

import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.NetItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.SpawnBlockConverter
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.util.miniMessage
import com.okkero.skedule.BukkitDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

/** 虫のスポーン処理をするイベントクラス */
class NetRightClickListener(
    private val plugin: Plugin,
    private val insectTypeTable: InsectTypeTable,
    private val insectItemConverter: InsectItemStackConverter,
    private val insectFrameConverter: InsectItemFrameConverter,
    private val netItemConverter: NetItemStackConverter,
    private val spawnBlockConverter: SpawnBlockConverter
) : Listener {
    private val coolDownPlayers = mutableSetOf<UUID>()

    /**
     * プレイヤーが虫網を右クリックするのを感知して虫をスポーンさせるメソッド
     *
     * @param event プレイヤーがエンティティやブロックをクリックしたのを感知するイベント
     */
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) &&
            event.hand == EquipmentSlot.HAND &&
            !player.isSprinting &&
            netItemConverter.isInsectNet(player.inventory.itemInMainHand)
        ) {
            // スポーンした虫が近くにいる場合はリターン
            val nearbyInsectFrames = event.player.location.getNearbyEntitiesByType(ItemFrame::class.java, RADIUS)
                .filter {
                    insectFrameConverter.isInsectFrame(it) &&
                        insectFrameConverter.loadTag(it) == SPAWN &&
                        insectItemConverter.isInsect(it.item) &&
                        insectItemConverter.insect(it.item).catcher == player
                }
            if (nearbyInsectFrames.isNotEmpty()) {
                return player.sendMessage(Config.messageConfig.spawnNowInsect.miniMessage())
            }

            // クールダウン中ならリターン
            if (coolDownPlayers.contains(player.uniqueId)) {
                return player.sendMessage(Config.messageConfig.catchCoolDown.miniMessage())
            }

            // スポーンするブロックがなければリターン
            val spawnBlock = spawnBlockConverter.pickRandomBlock(player.location.block)
            if (spawnBlock == null) {
                player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
                coolDown(player.uniqueId, Config.standardConfig.general.notSpawnCoolDown)
                return
            }

            // 発生できる虫がいなかったらリターン
            val insect = insectTypeTable.pickRandomType(
                catcher = player,
                material = spawnBlock.first.type,
                spawnDirection = spawnBlock.second
            )?.generateInsect(player)
            if (insect == null) {
                player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
                coolDown(player.uniqueId, Config.standardConfig.general.notSpawnCoolDown)
                return
            }

            val insectItem = insectItemConverter.createItemStack(insect)
            try {
                insectFrameConverter.spawn(spawnBlock.first, insectItem, spawnBlock.second.face)
                coolDown(player.uniqueId, Config.standardConfig.general.spawnCoolDown)
            } catch (error: IllegalArgumentException) {
                return player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
            }
        }
    }

    private fun coolDown(uuid: UUID, time: Long) {
        GlobalScope.launch(BukkitDispatcher(plugin as JavaPlugin)) {
            coolDownPlayers.add(uuid)
            delay(ONE_SECOND * time)
            coolDownPlayers.remove(uuid)
        }
    }

    companion object {
        private const val ONE_SECOND = 1000L
        private const val RADIUS = 16.0
        private const val SPAWN: Byte = 1
    }
}
