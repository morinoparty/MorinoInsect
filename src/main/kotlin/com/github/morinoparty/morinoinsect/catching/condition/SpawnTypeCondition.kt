package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.SpawnType
import org.bukkit.Material
import org.bukkit.entity.Player

class SpawnTypeCondition(
    private val type: SpawnType
) : Condition {
    override fun check(
        catcher: Player,
        block: Material,
        spawnType: SpawnType
    ): Boolean {
        return spawnType == type
    }
}
