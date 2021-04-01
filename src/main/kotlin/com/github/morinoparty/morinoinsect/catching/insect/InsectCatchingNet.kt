package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.configuration.toComponent
import com.github.morinoparty.morinoinsect.item.edit
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/** 虫あみのデータクラス */
@Serializable
data class InsectCatchingNet(
    val name: String = "",
    val id: String = "",
    val customModelData: Int? = null,
    val description: List<String> = listOf()
) {
    fun createInsectNet(): ItemStack {
        val material = Material.matchMaterial(name)
            ?: throw IllegalStateException("$id というアイテムは存在しません")
        val itemStack = ItemStack(material)

        itemStack.edit<ItemMeta> {
            displayName(name.toComponent())
            setCustomModelData(customModelData)
            lore(description.toComponent())
        }
        return itemStack
    }
}
