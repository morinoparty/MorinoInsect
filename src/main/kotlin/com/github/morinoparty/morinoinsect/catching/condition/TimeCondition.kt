package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class TimeCondition(
    private val state: Boolean
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnType: BlockFace
    ): Boolean {
        return catcher.world.isDayTime == state
    }
}
