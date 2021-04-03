package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.configuration.toComponent
import com.github.morinoparty.morinoinsect.item.edit
import com.github.morinoparty.morinoinsect.util.NamespacedKeyUtils
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
        val material = NamespacedKeyUtils.material(id)
        val itemStack = ItemStack(Material.CLOCK)
        Material.matchMaterial("minecraft:clock")
        itemStack.edit<ItemMeta> {
            displayName(name.toComponent())
            setCustomModelData(this@InsectCatchingNet.customModelData)
            lore(description.toComponent())
        }
        return itemStack
    }
}
