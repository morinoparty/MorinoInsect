package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.block.Block
import org.bukkit.entity.Player

class TimeCondition(
    private val state: Boolean
) : Condition {
    override fun check(
        catcher: Player,
        block: Block,
        spawnDirection: SpawnDirection
    ): Boolean {
        return catcher.world.isDayTime == state
    }
}
