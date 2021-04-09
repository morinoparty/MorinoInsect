package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnType
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

class SpawnTypeCondition(
    private val type: Collection<SpawnType>
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnType: SpawnType
    ): Boolean {
        return spawnType in type && checkSpawnLocationNotBuried(spawnType, block)
    }

    // スポーン場所がブロックで埋もれていないか確認するメソッド
    private fun checkSpawnLocationNotBuried(spawnType: SpawnType, spawnBlock: Block): Boolean {
        return when (spawnType) {
            SpawnType.EAST -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            SpawnType.WEST -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            SpawnType.SOUTH -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            SpawnType.NORTH -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            SpawnType.UP -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            SpawnType.DOWN -> spawnBlock.getRelative(spawnType.direction, 1).type == Material.AIR
            else -> throw IllegalStateException("予期しないエラーが起きました")
        }
    }
}
