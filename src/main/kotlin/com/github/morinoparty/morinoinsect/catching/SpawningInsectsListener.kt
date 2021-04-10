package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.area.AreaChecker
import com.github.morinoparty.morinoinsect.catching.area.AreaRange
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/** 虫のスポーン処理をするイベントクラス */
class SpawningInsectsListener(
    private val plugin: MorinoInsect,
    private val catchingNet: ItemStack
) : Listener {

    /**
     * プレイヤーが虫網を右クリックするのを感知して虫をスポーンさせるメソッド
     *
     * @param event プレイヤーがエンティティやブロックをクリックしたのを感知するイベント
     */
    @EventHandler
    fun onSpawn(event: PlayerInteractEvent) {
        val player = event.player
        val playerHasInsectCatchingNet =
            player.inventory.itemInMainHand == catchingNet
        val playerRightClicked =
            event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR
        if (!playerHasInsectCatchingNet) return
        if (!playerRightClicked) return
        val spawnBlock = detectBlocksAroundPlayer(player).random()
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
        val insectItemFrame = catcher.world.spawn(
            spawnBlock.location.add(0.0, 1.0, 0.0),
            ItemFrame::class.java
        ).also {
            it.setItem(insectItem)
            it.setFacingDirection(spawnDirection.direction)
            it.isVisible = false
        }

        // デバッグ用
        println("${insectItemFrame}がスポーンしました！")
    }

    // プレイヤーの周り（半径３ブロックの円）にあるブロックを検知して返すメソッド
    private fun detectBlocksAroundPlayer(player: Player): MutableSet<Block> {
        val playerLocBlock = player.location.block
        val detectArea = AreaRange(-3..3, -1..3, -3..3)
        val detectedBlocksSet: MutableSet<Block> = mutableSetOf()

        // detectAreaで指定した範囲のブロックを検出してdetectedBlocksSetに入れる
        for (x in detectArea.x) {
            for (y in detectArea.y) {
                for (z in detectArea.z) {
                    if (AreaChecker(x, z).isSquareCorner()) continue
                    val detectedBlock = playerLocBlock.getRelative(x, y, z)
                    if (detectedBlock.type == Material.AIR) continue
                    if (!canBlockSpawnInsects(detectedBlock)) continue
                    detectedBlocksSet.add(detectedBlock)
                }
            }
        }
        return detectedBlocksSet
    }

    // ブロックが虫をスポーンできる面があるかどうか確認するメソッド
    private fun canBlockSpawnInsects(spawnBlock: Block): Boolean {
        val relativeBlocks: MutableList<Block> = mutableListOf(
            spawnBlock.getRelative(SpawnDirection.DOWN.direction, 1),
            spawnBlock.getRelative(SpawnDirection.UP.direction, 1),
            spawnBlock.getRelative(SpawnDirection.NORTH.direction, 1),
            spawnBlock.getRelative(SpawnDirection.SOUTH.direction, 1),
            spawnBlock.getRelative(SpawnDirection.EAST.direction, 1),
            spawnBlock.getRelative(SpawnDirection.WEST.direction, 1)
        )
        var spawnableFaceCount = 0
        relativeBlocks.forEach { block ->
            if (block.type == Material.AIR) return@forEach
            spawnableFaceCount++
        }
        return spawnableFaceCount >= 1
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
