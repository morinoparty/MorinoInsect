package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.Material
import org.bukkit.entity.Player

class BlockCondition(
    private val blocks: Collection<Material>
) : Condition {
    override fun check(
        catcher: Player,
        block: Material
    ): Boolean {
        return true
        TODO("Not yet implemented")
    }
}
