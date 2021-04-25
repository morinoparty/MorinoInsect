package com.github.morinoparty.morinoinsect.catching.insect

import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.random.Random

class MutableInsectTypeTable : HashMap<InsectRarity, Set<InsectType>>(), InsectTypeTable {
    override val rarities: Set<InsectRarity>
        get() = keys.toSet()
    override val types: Set<InsectType>
        get() = values.flatten().toSet()

    override fun pickRandomRarity(): InsectRarity {
        val completeWeight = rarities.sumByDouble {
            it.chance
        }
        val random = Random.nextDouble() * completeWeight
        var countWeight = 0.0
        for (rarity in rarities) {
            countWeight += rarity.chance
            if (countWeight >= random) return rarity
        }
        return rarities.maxByOrNull { it.chance }
            ?: throw IllegalStateException("レアリティを追加して下さい")
    }

    override fun pickRandomType(
        catcher: Player,
        material: Material,
        rarity: InsectRarity,
        spawnDirection: SpawnDirection
    ): InsectType? {
        check(contains(rarity)) { "Rarity must be contained in the table" }
        val type = types.filter {
            it.rarity == rarity && it.conditions
                .all { condition -> condition.check(catcher, material, spawnDirection) }
        }
        if (type.isEmpty()) return null
        return type.random()
    }
}
