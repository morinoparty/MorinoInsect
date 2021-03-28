package com.github.morinoparty.morinoinsect.item

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class InsectItemTagWriter(
    private val insectRarityKey: NamespacedKey,
    private val insectLengthKey: NamespacedKey
) {
    fun write(itemMeta: ItemMeta, rarity: String, length: Int) {
        itemMeta.persistentDataContainer.run {
            set(insectRarityKey, PersistentDataType.STRING, rarity)
            set(insectLengthKey, PersistentDataType.INTEGER, length)
        }
    }
}
