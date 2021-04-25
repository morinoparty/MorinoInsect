package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.entity.Player

class LocationYCondition(
    private val range: IntRange
) : InsectCondition {
    override fun check(
        catcher: Player,
        material: Material,
        spawnDirection: SpawnDirection
    ): Boolean {
        return catcher.location.blockY in range
    }
}
