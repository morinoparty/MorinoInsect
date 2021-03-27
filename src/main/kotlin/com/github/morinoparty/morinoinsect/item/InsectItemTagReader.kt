package com.github.morinoparty.morinoinsect.item

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class InsectItemTagReader(
    private val insectTypeTable: InsectTypeTable,
    private val insectTypeKey: NamespacedKey,
    private val insectLengthKey: NamespacedKey
) {

    fun canRead(itemMeta: ItemMeta): Boolean {
        return itemMeta.persistentDataContainer.let { data ->
            data.has(insectTypeKey, PersistentDataType.STRING) && data.has(insectLengthKey, PersistentDataType.DOUBLE)
        }
    }

    fun read(itemMeta: ItemMeta): Insect {
        return itemMeta.persistentDataContainer.let { data ->
            require(data.has(insectTypeKey, PersistentDataType.STRING)) { "昆虫のレア度タグが必要です" }
            require(data.has(insectLengthKey, PersistentDataType.INTEGER)) { "昆虫の長さタグが必要です" }
            val typeName = data.get(insectTypeKey, PersistentDataType.STRING)
            val type = insectTypeTable.insectMap[typeName]
                ?: throw IllegalStateException("昆虫が存在しません")
            val length = data.get(insectLengthKey, PersistentDataType.INTEGER)
                ?: throw IllegalStateException("昆虫の長さが存在しません")
            Insect(type, length)
        }
    }
}
