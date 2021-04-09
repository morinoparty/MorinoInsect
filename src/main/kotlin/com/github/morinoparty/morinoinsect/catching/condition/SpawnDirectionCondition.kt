package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

class SpawnDirectionCondition(
    private val direction: Collection<SpawnDirection>
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnDirection: SpawnDirection
    ): Boolean {
        return spawnDirection in direction && checkSpawnLocationNotBuried(spawnDirection, block)
    }

    // スポーン場所がブロックで埋もれていないか確認するメソッド
    private fun checkSpawnLocationNotBuried(spawnDirection: SpawnDirection, spawnBlock: Block): Boolean {
        return when (spawnDirection) {
            SpawnDirection.EAST -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            SpawnDirection.WEST -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            SpawnDirection.SOUTH -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            SpawnDirection.NORTH -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            SpawnDirection.UP -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            SpawnDirection.DOWN -> spawnBlock.getRelative(spawnDirection.direction, 1).type == Material.AIR
            else -> throw IllegalStateException("予期しないエラーが起きました")
        }
    }
}
