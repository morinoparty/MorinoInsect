package com.github.morinoparty.morinoinsect.catching.converter

import com.github.morinoparty.morinoinsect.catching.tag.NetItemTagManager
import com.github.morinoparty.morinoinsect.util.edit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

class NetItemStackConverter(
    plugin: Plugin
) {
    private val netItemTagConverter: NetItemTagManager

    init {
        val netTag = NamespacedKey(plugin, "netType")
        netItemTagConverter = NetItemTagManager(netTag)
    }

    fun isInsectNet(itemStack: ItemStack): Boolean {
        val meta = if (itemStack.hasItemMeta()) {
            itemStack.itemMeta
        } else return false
        return netItemTagConverter.canRead(meta)
    }

    fun loadTag(itemStack: ItemStack): Byte {
        val meta = itemStack.itemMeta
            ?: throw IllegalStateException("ItemMetaが存在しません")
        return netItemTagConverter.read(meta)
    }

    fun createInsectNet(itemStack: ItemStack): ItemStack {
        itemStack.edit<ItemMeta> {
            netItemTagConverter.write(this, INSECT_NET)
        }
        return itemStack
    }

    companion object {
        private const val INSECT_NET: Byte = 1
    }
}
