package com.github.morinoparty.morinoinsect.catching.util

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/** 虫網のインスタンスを作成するだけの専用クラス */
class InsectCatchingNet {

    // 他のクラスでこのメソッドを仕様するときにクラスの宣言無しで使える ex: InsectCatchingNet.createNetInstance()
    companion object {
        /**
         * 虫網のインスタンスを渡すメソッド
         *
         * @return 虫網のインスタンス
         */
        fun createNetInstance(): ItemStack {
            val insectCatchingNet = ItemStack(Material.FISHING_ROD, 1)
            val itemMeta = insectCatchingNet.itemMeta
            val displayNameComponent = Component.text("虫取り網の名前をここに入力")
            val loreComponent: MutableList<Component> = ArrayList()
            val lineOf1 = Component.text("ここに説明文を入力")
            itemMeta.displayName(displayNameComponent)
            loreComponent.add(lineOf1)
            itemMeta.lore(loreComponent)
            insectCatchingNet.itemMeta = itemMeta
            return insectCatchingNet
        }
    }
}
