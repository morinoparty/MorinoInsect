package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.area.SpawnType
import org.bukkit.Material
import org.bukkit.entity.Player

class BlockCondition(
    private val blocks: Collection<Material>
) : Condition {
    override fun check(
        catcher: Player,
        block: Material,
        spawnType: SpawnType
    ): Boolean {
        return block in blocks
    }
}
