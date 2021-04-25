package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.entity.Player

class BiomeCondition(
    private val biomes: Collection<Biome>
) : InsectCondition {
    override fun check(
        catcher: Player,
        material: Material,
        spawnDirection: SpawnDirection
    ): Boolean {
        val x = catcher.location.blockX
        val y = catcher.location.blockY
        val z = catcher.location.blockZ
        return catcher.world.getBiome(x, y, z) in biomes
    }
}
