package com.github.morinoparty.morinoinsect.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Net {

    // javaのstatic関数を再現
    companion object {
        @JvmStatic
        fun createNetInstance(): ItemStack {
            val net                       = ItemStack(Material.FISHING_ROD, 1)
            val itemMeta                  = net.itemMeta
            itemMeta.setDisplayName("虫取り網の名前をここに入力")
            val lore: MutableList<String> = ArrayList()
            lore.add("ここに説明文を入力")
            itemMeta.lore                 = lore
            net.itemMeta                  = itemMeta
            return net
        }
    }
}