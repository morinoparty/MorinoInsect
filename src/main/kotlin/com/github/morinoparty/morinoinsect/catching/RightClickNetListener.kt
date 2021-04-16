package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.area.AreaChecker
import com.github.morinoparty.morinoinsect.catching.area.AreaRange
import com.github.morinoparty.morinoinsect.catching.area.SpawnLocationAdjuster
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
class RightClickNetListener(
    private val plugin: MorinoInsect,
    private val catchingNet: ItemStack
) : Listener, SpawnLocationAdjuster {

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
        if (!playerHasInsectCatchingNet) return
        if (!playerRightClicked) return
        if (!player.isSneaking) return
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

    // プレイヤーの周り（半径３ブロックの円）にあるブロックを検知して返すメソッド
    private fun detectBlocksAroundPlayer(player: Player): MutableSet<Block> {
        val playerLocBlock = player.location.block
        val detectArea = AreaRange(-8..8, -1..8, -8..8)
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

    // ブロックが虫をスポーンできる面が１つでもあるかどうか確認するメソッド
    private fun canBlockSpawnInsects(spawnBlock: Block): Boolean {
        var spawnableFaceCount = 0
        val relativeBlocks: MutableList<Block> = mutableListOf(
            spawnBlock.getRelative(SpawnDirection.DOWN.direction, 1),
            spawnBlock.getRelative(SpawnDirection.UP.direction, 1),
            spawnBlock.getRelative(SpawnDirection.NORTH.direction, 1),
            spawnBlock.getRelative(SpawnDirection.SOUTH.direction, 1),
            spawnBlock.getRelative(SpawnDirection.EAST.direction, 1),
            spawnBlock.getRelative(SpawnDirection.WEST.direction, 1)
        )
        relativeBlocks.forEach { block ->
            if (block.type != Material.AIR) return@forEach
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
