package com.github.morinoparty.morinoinsect.configuration.loader

import com.github.morinoparty.morinoinsect.catching.condition.BiomeCondition
import com.github.morinoparty.morinoinsect.catching.condition.BlockCondition
import com.github.morinoparty.morinoinsect.catching.condition.DirectionCondition
import com.github.morinoparty.morinoinsect.catching.condition.InsectCondition
import com.github.morinoparty.morinoinsect.catching.condition.LocationYCondition
import com.github.morinoparty.morinoinsect.catching.condition.RainingCondition
import com.github.morinoparty.morinoinsect.catching.condition.ThunderingCondition
import com.github.morinoparty.morinoinsect.catching.condition.TimeCondition
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import com.github.morinoparty.morinoinsect.configuration.data.ConditionConfig
import org.bukkit.Material
import org.bukkit.block.Biome

class InsectConditionSetLoader {
    fun loadFrom(condition: ConditionConfig): Set<InsectCondition> {
        return setOfNotNull(
            BlockCondition(
                condition.blocks.map {
                    Material.valueOf(it.toUpperCase())
                }
            ),
            DirectionCondition(
                condition.directions.map {
                    SpawnDirection.valueOf(it.toUpperCase())
                }
            ),
            condition.time?.let {
                TimeCondition(TimeCondition.TimeState.valueOf(it.toUpperCase()))
            },
            condition.biomes?.let {
                BiomeCondition(
                    it.map { biomeString ->
                        Biome.valueOf(biomeString.toUpperCase())
                    }
                )
            },
            condition.locationY?.let {
                LocationYCondition(
                    it.toIntRange()
                )
            },
            condition.raining?.let {
                RainingCondition(it)
            },
            condition.thundering?.let {
                ThunderingCondition(it)
            }
        )
    }

    private fun String.toIntRange(): IntRange {
        val tokens = this.split(DELIMITER)
        return tokens[0].toInt()..tokens[1].toInt()
    }

    companion object {
        private const val DELIMITER = ".."
    }
}
