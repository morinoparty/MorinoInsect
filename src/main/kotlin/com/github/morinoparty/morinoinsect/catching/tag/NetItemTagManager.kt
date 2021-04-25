package com.github.morinoparty.morinoinsect.catching.tag

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class NetItemTagManager(
    private val netKey: NamespacedKey,
) {
    fun write(itemMeta: ItemMeta, byte: Byte) {
        itemMeta.persistentDataContainer.run {
            set(netKey, PersistentDataType.BYTE, byte)
        }
    }

    fun canRead(itemMeta: ItemMeta): Boolean {
        return itemMeta.persistentDataContainer.has(netKey, PersistentDataType.BYTE)
    }

    fun read(itemMeta: ItemMeta): Byte {
        return itemMeta.persistentDataContainer.let { data ->
            require(data.has(netKey, PersistentDataType.BYTE)) { "タグがありません" }
            data.get(netKey, PersistentDataType.BYTE)
                ?: throw IllegalStateException("タグがありません")
        }
    }
}
