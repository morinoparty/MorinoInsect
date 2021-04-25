package com.github.morinoparty.morinoinsect.catching.converter

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.catching.tag.InsectItemTagManager
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.configuration.data.ItemFormatConfig
import com.github.morinoparty.morinoinsect.configuration.format.TextListFormat
import com.github.morinoparty.morinoinsect.util.edit
import com.github.morinoparty.morinoinsect.util.miniMessage
import com.github.morinoparty.morinoinsect.util.plainText
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.Template
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

class InsectItemStackConverter(
    plugin: Plugin,
    insectTypeTable: InsectTypeTable
) {
    private val insectItemTagManager: InsectItemTagManager
    private val formatConfig: ItemFormatConfig
        get() = Config.insectConfig.itemFormat

    init {
        val insectTypeKey = NamespacedKey(plugin, "type")
        val insectLengthKey = NamespacedKey(plugin, "length")
        val insectCatcherKey = NamespacedKey(plugin, "catcher")
        insectItemTagManager = InsectItemTagManager(insectTypeTable, insectTypeKey, insectLengthKey, insectCatcherKey)
    }

    /**
     * アイテムが昆虫かどうかを確認するメソッド
     *
     * @return 昆虫ならtrueを返します
     */
    fun isInsect(itemStack: ItemStack): Boolean {
        val meta = if (itemStack.hasItemMeta()) {
            itemStack.itemMeta
        } else return false
        return insectItemTagManager.canRead(meta)
    }

    /**
     * 昆虫のレアリティと大きさを取得するメソッド
     *
     * @return レアリティと大きさを返します
     */
    fun insect(itemStack: ItemStack): Insect {
        val meta = itemStack.itemMeta
            ?: throw IllegalStateException("ItemMetaが存在しません")
        return insectItemTagManager.read(meta)
    }

    /**
     * 虫のアイテムを作成するメソッド
     *
     * @return 額縁にがっちゃんこするためのアイテムを返します
     */
    fun createItemStack(insect: Insect): ItemStack {
        val itemStack = insect.type.icon.clone()
        val replacement = getFormatReplacementMap(insect)
        itemStack.edit<ItemMeta> {
            val plainName = displayName()?.plainText()
                ?: insect.type.name
            displayName(
                Component
                    .text(plainName)
                    .color(insect.type.rarity.color)
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.ITALIC, false)
            )
            val description = TextListFormat(formatConfig.lore).replace(insect.catcher).miniMessage(replacement)
            lore(description + (lore() ?: emptyList()))
            insectItemTagManager.write(this, insect)
        }
        return itemStack
    }

    private fun getFormatReplacementMap(insect: Insect): List<Template> {
        val insectName = insect.type.icon.itemMeta.displayName()?.plainText() ?: insect.type.name
        return listOf(
            Template.of("player", insect.catcher.name),
            Template.of("rarity", insect.catcher.name),
            Template.of("length", insect.length.toString()),
            Template.of("insect", insectName)
        )
    }
}
