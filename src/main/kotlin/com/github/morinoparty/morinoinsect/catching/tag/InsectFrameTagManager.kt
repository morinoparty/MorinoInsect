package com.github.morinoparty.morinoinsect.catching.tag

import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemFrame
import org.bukkit.persistence.PersistentDataType
import java.lang.IllegalStateException

class InsectFrameTagManager(
    private val itemFrameKey: NamespacedKey
) {
    fun write(itemFrame: ItemFrame, byte: Byte) {
        itemFrame.persistentDataContainer.run {
            set(itemFrameKey, PersistentDataType.BYTE, byte)
        }
    }

    fun canRead(entity: Entity): Boolean {
        if (entity !is ItemFrame) return false
        return entity.persistentDataContainer.has(itemFrameKey, PersistentDataType.BYTE)
    }

    fun read(itemFrame: ItemFrame): Byte {
        return itemFrame.persistentDataContainer.let { data ->
            require(data.has(itemFrameKey, PersistentDataType.BYTE)) { "額縁のタグが必要です" }
            data.get(itemFrameKey, PersistentDataType.BYTE)
                ?: throw IllegalStateException("昆虫のタグが必要です")
        }
    }
}
