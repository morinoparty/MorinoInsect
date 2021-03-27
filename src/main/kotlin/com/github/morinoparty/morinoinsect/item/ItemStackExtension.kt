package com.github.morinoparty.morinoinsect.item

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

inline fun <reified T : ItemMeta> ItemStack.edit(block: T.() -> Unit) {
    itemMeta = (itemMeta as T).apply(block)
}

inline fun <reified T : ItemMeta> ItemStack.editIfHas(block: T.() -> Unit) {
    if (itemMeta is T) {
        itemMeta = (itemMeta as T).apply(block)
    }
}

inline fun <reified T> ItemStack.editIfIs(block: T.() -> Unit) {
    if (itemMeta is T) {
        val newMeta = itemMeta as T
        newMeta.apply(block)
        itemMeta = newMeta as ItemMeta
    }
}
