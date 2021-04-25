package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.entity.Player

class TimeCondition(
    private val state: TimeState
) : InsectCondition {
    override fun check(
        catcher: Player,
        material: Material,
        spawnDirection: SpawnDirection
    ): Boolean {
        return catcher.world.isDayTime == state.isDayTime
    }

    enum class TimeState(
        val isDayTime: Boolean
    ) {
        DAY(true),
        NIGHT(false)
    }
}
