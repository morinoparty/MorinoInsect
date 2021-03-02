package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.util.Vector
import com.google.common.collect.ImmutableList
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.collections.ArrayList
import kotlin.random.Random

class SpawningInsectsListener : Listener {

    private val WalkCount = mutableMapOf<Player, Int>()

    fun onSpawn(event: PlayerMoveEvent) {
        val player = event.player
        if (!isPlayerInPlayerWalkCountMap(player)) {
            WalkCount[player] = 0
        } else {
            val walkCount = WalkCount[player]!! + 1
            WalkCount[player] = walkCount
            // ３０ブロック歩いたら周りに虫をスポーンさせる
            if (walkCount < 30) return
        }
    }

    private fun isPlayerInPlayerWalkCountMap(player: Player): Boolean {
        return WalkCount.containsKey(player)
    }

    private fun detectBlocksAroundPlayer(player: Player): MutableList<Block> {
        // プレイヤーの真下のブロックの位置を取得したいため、y座標を-1した
        val playerLocation = player.location.subtract(0.0, 1.0, 0.0)
        val detectingBlocksArea = Vector(6, 3, 6)
        // 指定した範囲にあるブロックを検知してListに一つずつ入れていく
        val blocks: MutableList<Block> = ArrayList()
        for (x in 0..detectingBlocksArea.x step 1) {
            for (z in 0..detectingBlocksArea.z step 1) {
                for (y in 0..detectingBlocksArea.y step 1) {
                    val detectArea = playerLocation.add(x.toDouble(), y.toDouble(), z.toDouble())
                    val detectedBlock = detectArea.block
                    if (!isGrassBlockOrLeaves(detectedBlock)) continue
                    blocks.add(detectedBlock)
                }
            }
        }
        return blocks
    }

    private fun isGrassBlockOrLeaves(block: Block): Boolean {
        return block.equals(Material.GRASS_BLOCK) ||
            block.equals(Material.ACACIA_LEAVES) ||
            block.equals(Material.OAK_LEAVES) ||
            block.equals(Material.DARK_OAK_LEAVES) ||
            block.equals(Material.SPRUCE_LEAVES) ||
            block.equals(Material.BIRCH_LEAVES) ||
            block.equals(Material.JUNGLE_LEAVES)
    }

    private fun spawnInsects(player: Player, blocks: ImmutableList<Block>) {
        val maxBlocksCountInBlocksList = 108
        val randomIndex = Random.nextInt(until = maxBlocksCountInBlocksList)
        val blockFromRandomIndex = blocks[randomIndex]
        val blockLocation = blockFromRandomIndex.location
        // ToDo: この続きに虫をスポーンさせる処理を書く
    }
}
