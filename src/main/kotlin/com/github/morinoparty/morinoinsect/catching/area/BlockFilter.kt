package com.github.morinoparty.morinoinsect.catching.area

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * 検知したブロックのフィルターの役割をするクラス
 */
interface BlockFilter {

    /**
     * 虫がスポーン可能なブロックだけを抜き取るメソッド
     */
    fun Collection<Block>.takeSpawnable(): Collection<Block> = this
        .filter { it.type != Material.AIR }
        .filter { it.type != Material.GRASS }
        .filter { it.type != Material.TALL_GRASS }
        .filter { hasSpawnableFace(it) }

    // ブロックが虫をスポーンできる面が１つでもあるかどうか確認するメソッド
    private fun hasSpawnableFace(spawnBlock: Block): Boolean {
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
}
