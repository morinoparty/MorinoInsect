package com.github.morinoparty.morinoinsect.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class InsectCatchingNet {

    // 他のクラスでこのメソッドを仕様するときにクラスの宣言無しで使える ex: InsectCatchingNet.createNetInstance()
    companion object {
        @JvmStatic
        fun createNetInstance(): ItemStack {
            val insectCatchingNet = ItemStack(Material.FISHING_ROD, 1)
            val itemMeta = insectCatchingNet.itemMeta
            itemMeta.setDisplayName("虫取り網の名前をここに入力")
            val lore: MutableList<String> = ArrayList()
            lore.add("ここに説明文を入力")
            itemMeta.lore = lore
            insectCatchingNet.itemMeta = itemMeta
            return insectCatchingNet
        }
    }
}
