package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class BlockCondition(
    private val blocks: Collection<Material>
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnType: BlockFace
    ): Boolean {
        return block.type in blocks
    }
}
