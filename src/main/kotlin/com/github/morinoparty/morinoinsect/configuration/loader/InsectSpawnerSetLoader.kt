package com.github.morinoparty.morinoinsect.configuration.loader

import com.github.morinoparty.morinoinsect.configuration.data.InsectTypeConfig
import org.bukkit.Material

class InsectSpawnerSetLoader {
    fun loadFrom(types: Map<String, InsectTypeConfig>): Set<Material> {
        val materials = mutableSetOf<Material>()
        for (type in types.values) {
            for (block in type.conditions.blocks) {
                val material = Material.matchMaterial(block)
                material?.let { materials.add(it) }
            }
        }
        return materials
    }
}
