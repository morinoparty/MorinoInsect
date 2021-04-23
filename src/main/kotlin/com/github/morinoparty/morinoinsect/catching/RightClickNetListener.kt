package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.area.BlockDetector
import com.github.morinoparty.morinoinsect.catching.area.BlockFilter
import com.github.morinoparty.morinoinsect.catching.area.SpawnLocationAdjuster
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.block.Block
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/** 虫のスポーン処理をするイベントクラス */
class RightClickNetListener(
    private val plugin: MorinoInsect,
    private val catchingNet: ItemStack
) : Listener, SpawnLocationAdjuster, BlockFilter {

    /**
     * プレイヤーが虫網を右クリックするのを感知して虫をスポーンさせるメソッド
     *
     * @param event プレイヤーがエンティティやブロックをクリックしたのを感知するイベント
     */
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val playerHasInsectCatchingNet =
            player.inventory.itemInMainHand == catchingNet
        val playerRightClicked =
            event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR
        if (!playerHasInsectCatchingNet &&
            !playerRightClicked &&
            !player.isSneaking
        ) return
        val detectBlocksRadius = 8
        val spawnBlock =
            BlockDetector(player.location, detectBlocksRadius).detectBlocksOfSphere().takeSpawnable().random()
        val spawnType = SpawnDirection.values().random()
        spawnInsect(player, spawnBlock, spawnType)
    }

    // 虫をランダムに選んでスポーンさせるメソッド
    private fun spawnInsect(catcher: Player, spawnBlock: Block, spawnDirection: SpawnDirection) {
        val insect: Insect = plugin.insectTypeTable.pickRandomType(
            catcher,
            spawnBlock,
            spawnDirection
        )?.let {
            sendInsectsFoundMessage(catcher)
            it.generateInsect()
        } ?: run {
            sendNoInsectsFoundMessage(catcher)
            return
        }
        val insectItem = plugin.converter.createItemStack(catcher, insect)
        val spawnLocation = spawnBlock.location.adjustSpawnLocation(spawnDirection.direction)
        val insectItemFrame = catcher.world.spawn(
            spawnLocation,
            ItemFrame::class.java
        ) {
            it.setItem(insectItem)
            it.setFacingDirection(spawnDirection.direction, false)
            it.isVisible = false
            it.isFixed = true
        }

        // デバッグ用
        println("${insectItemFrame}がスポーンしました！")
    }

    // 条件に当てはまる虫がいなかった時にプレイヤーに送信するメッセージメソッド
    private fun sendNoInsectsFoundMessage(catcher: Player) {
        catcher.sendMessage("虫はいないようだ...")
    }

    // 条件に当てはまる虫がいた時にプレイヤーに送信するメッセージメソッド
    private fun sendInsectsFoundMessage(catcher: Player) {
        catcher.sendMessage("耳を済ませていたら、虫の声が聞こえてきた...")
    }
}
