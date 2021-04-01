package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.SpawnType
import org.bukkit.Material
import org.bukkit.entity.Player

class SpawnTypeCondition(
    private val spawnType: SpawnType
) : Condition {
    override fun check(
        catcher: Player,
        block: Material
    ): Boolean {
        return convertToSpawnType(block) == spawnType
    }

    private fun convertToSpawnType(block: Material): SpawnType {
        val treeBlocks = arrayOf(
            Material.OAK_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.SPRUCE_LOG
        )
        return when (block in treeBlocks) {
            true -> SpawnType.ONTREE
            false -> SpawnType.ONGROUND
        }
    }
}
