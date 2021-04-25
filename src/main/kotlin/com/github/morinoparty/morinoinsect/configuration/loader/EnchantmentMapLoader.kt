package com.github.morinoparty.morinoinsect.configuration.loader

import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment

class EnchantmentMapLoader {
    fun loadFrom(enchantments: List<String>): Map<Enchantment, Int> {
        return enchantments.associate {
            val tokens = it.split(DELIMITER)
            val id = NamespacedKey.minecraft(tokens[0])
            val enchantment: Enchantment = Enchantment.getByKey(id)
                ?: throw IllegalStateException("IDが${id}のエンチャントは存在しません")
            val level = tokens[1].toInt()
            Pair(enchantment, level)
        }
    }

    companion object {
        private const val DELIMITER = '|'
    }
}
