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
            if (coolDownPlayers.contains(player.uniqueId)) {
                return player.sendMessage(Config.messageConfig.catchCoolDown.miniMessage())
            }

            coolDown(player.uniqueId)

            val spawnBlock = spawnBlockConverter.pickRandomBlock(player.location.block)
                ?: return player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())

            val insect = insectTypeTable.pickRandomType(
                catcher = player,
                material = spawnBlock.first.type,
                spawnDirection = spawnBlock.second
            )?.generateInsect(player)
                ?: return player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
            val insectItem = insectItemConverter.createItemStack(insect)

            try {
                insectFrameConverter.spawn(spawnBlock.first, insectItem, spawnBlock.second.face)
            } catch (error: IllegalArgumentException) {
                return player.sendMessage(Config.messageConfig.notSpawnInsect.miniMessage())
            }
        }
    }

    private fun coolDown(uuid: UUID) {
        GlobalScope.launch(BukkitDispatcher(plugin as JavaPlugin)) {
            coolDownPlayers.add(uuid)
            delay(ONE_SECOND * Config.standardConfig.general.catchCoolDown)
            coolDownPlayers.remove(uuid)
        }
    }

    companion object {
        private const val ONE_SECOND = 1000L
    }
}
