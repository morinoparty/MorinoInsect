package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.area.SpawnType
import org.bukkit.Material
import org.bukkit.entity.Player

class TimeCondition(
    private val state: Boolean
) : Condition {
    override fun check(
        catcher: Player,
        block: Material,
        spawnType: SpawnType
    ): Boolean {
        return catcher.world.isDayTime == state
    }
}
