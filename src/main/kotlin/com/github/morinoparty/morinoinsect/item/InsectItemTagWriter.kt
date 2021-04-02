package com.github.morinoparty.morinoinsect.item

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class InsectItemTagWriter(
    private val insectTypeKey: NamespacedKey,
    private val insectLengthKey: NamespacedKey
) {
    fun write(itemMeta: ItemMeta, insect: Insect) {
        itemMeta.persistentDataContainer.run {
            set(insectTypeKey, PersistentDataType.STRING, insect.type.name)
            set(insectLengthKey, PersistentDataType.INTEGER, insect.length)
        }
    }
}
