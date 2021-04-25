package com.github.morinoparty.morinoinsect.catching.tag

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

class InsectItemTagManager(
    private val insectTypeTable: InsectTypeTable,
    private val insectTypeKey: NamespacedKey,
    private val insectLengthKey: NamespacedKey,
    private val insectCatcherKey: NamespacedKey
) {
    fun write(itemMeta: ItemMeta, insect: Insect) {
        itemMeta.persistentDataContainer.run {
            set(insectTypeKey, PersistentDataType.STRING, insect.type.name)
            set(insectLengthKey, PersistentDataType.DOUBLE, insect.length)
            set(insectCatcherKey, PersistentDataType.STRING, insect.catcher.uniqueId.toString())
        }
    }

    fun canRead(itemMeta: ItemMeta): Boolean {
        return itemMeta.persistentDataContainer.let { data ->
            data.has(insectTypeKey, PersistentDataType.STRING) &&
                data.has(insectLengthKey, PersistentDataType.DOUBLE) &&
                data.has(insectCatcherKey, PersistentDataType.STRING)
        }
    }

    fun read(itemMeta: ItemMeta): Insect {
        return itemMeta.persistentDataContainer.let { data ->
            require(data.has(insectTypeKey, PersistentDataType.STRING)) { "昆虫の名前が必要です" }
            require(data.has(insectLengthKey, PersistentDataType.DOUBLE)) { "昆虫の長さタグが必要です" }
            require(data.has(insectCatcherKey, PersistentDataType.STRING)) { "キャッチャーのUUIDが必要です" }

            val typeName = data.get(insectTypeKey, PersistentDataType.STRING)
            val type = insectTypeTable.types.find { it.name == typeName }
                ?: throw IllegalStateException("昆虫が存在しません")

            val length = data.get(insectLengthKey, PersistentDataType.DOUBLE)
                ?: throw IllegalStateException("昆虫の長さが存在しません")

            val catcherUniqueId = UUID.fromString(data.get(insectCatcherKey, PersistentDataType.STRING))
            val catcher = Bukkit.getPlayer(catcherUniqueId)
                ?: throw IllegalStateException("キャッチャーが存在しません")

            Insect(type, length, catcher)
        }
    }
}
