package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class SpawnTypeCondition(
    private val type: Collection<BlockFace>
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnType: BlockFace
    ): Boolean {
        return spawnType in type && checkSpawnLocationNotBuried(spawnType, block)
    }

    // スポーン場所がブロックで埋もれていないか確認するメソッド
    private fun checkSpawnLocationNotBuried(spawnType: BlockFace, spawnBlock: Block): Boolean {
        return when (spawnType) {
            BlockFace.EAST -> spawnBlock.getRelative(spawnType).type == Material.AIR
            BlockFace.WEST -> spawnBlock.getRelative(spawnType).type == Material.AIR
            BlockFace.SOUTH -> spawnBlock.getRelative(spawnType).type == Material.AIR
            BlockFace.NORTH -> spawnBlock.getRelative(spawnType).type == Material.AIR
            BlockFace.UP -> spawnBlock.getRelative(spawnType).type == Material.AIR
            BlockFace.DOWN -> spawnBlock.getRelative(spawnType).type == Material.AIR
            else -> throw IllegalStateException("予期しないエラーが起きました")
        }
    }
}
