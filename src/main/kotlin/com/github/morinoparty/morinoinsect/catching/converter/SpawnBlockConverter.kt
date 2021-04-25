package com.github.morinoparty.morinoinsect.catching.converter

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import kotlin.math.sqrt
import kotlin.random.Random

class SpawnBlockConverter(private val materials: Set<Material>) {
    fun pickRandomBlock(block: Block): Pair<Block, SpawnDirection>? {
        val spawnBlocks = donutSphereBlocks(block.location, 12)
        if (spawnBlocks.isEmpty()) return null

        val randomMaterial = pickRandomMaterial(spawnBlocks.keys)
        val filterBlocks = spawnBlocks.filter { it.key.type == randomMaterial }

        val spawnBlock = filterBlocks.entries.elementAt(Random.nextInt(filterBlocks.size)).toPair()
        val face = spawnBlock.second.random()

        return Pair(spawnBlock.first, face)
    }

    // プレイヤーの周りにあるブロックを検知して返すメソッド
    private fun donutSphereBlocks(center: Location, radius: Int): Map<Block, Set<SpawnDirection>> {
        val spawnBlocks = mutableMapOf<Block, Set<SpawnDirection>>()
        for (x in -radius..radius) {
            for (y in -radius / 3..radius / 3) {
                for (z in -radius..radius) {
                    if (x in -radius / 3..radius / 3 &&
                        z in -radius / 3..radius / 3
                    ) continue
                    if (sqrt((x * x) + (y * y) + (z * z).toDouble()) <= radius) {
                        val relativeBlock = center.world.getBlockAt(x + center.blockX, y + center.blockY, z + center.blockZ)
                        if (relativeBlock.type == Material.AIR ||
                            relativeBlock.type == Material.CAVE_AIR ||
                            relativeBlock.type == Material.VOID_AIR ||
                            relativeBlock.type !in materials
                        ) continue

                        val spawnableFaces = spawnFaces(relativeBlock)
                        if (spawnableFaces.isNullOrEmpty()) continue

                        spawnBlocks[relativeBlock] = spawnableFaces
                    }
                }
            }
        }
        return spawnBlocks
    }

    // 空気ブロックに隣接している面を返すメソッド
    private fun spawnFaces(block: Block): Set<SpawnDirection> {
        val spawnableFaces = mutableSetOf<SpawnDirection>()
        for (direction in SpawnDirection.values()) {
            if (block.getRelative(direction.face).type == Material.AIR) {
                spawnableFaces.add(direction)
            }
        }
        return spawnableFaces
    }

    private fun pickRandomMaterial(blocks: Set<Block>): Material {
        val materials = mutableSetOf<Material>()
        for (block in blocks) {
            materials.add(block.type)
        }
        return materials.random()
    }
}
