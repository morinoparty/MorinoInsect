package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnType
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

class BlockCondition(
    private val blocks: Collection<Material>
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnType: SpawnType
    ): Boolean {
        return block.type in blocks
    }
}
