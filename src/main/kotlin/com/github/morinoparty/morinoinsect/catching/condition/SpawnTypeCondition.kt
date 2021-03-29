package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.Material
import org.bukkit.entity.Player

class SpawnTypeCondition(
    private val type: String
) : Condition {
    override fun check(
        catcher: Player,
        block: Material,
        spawnType: String
    ): Boolean {
        return spawnType == type
    }
}
