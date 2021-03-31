package com.github.morinoparty.morinoinsect.item

import com.destroystokyo.paper.profile.ProfileProperty
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.configuration.toComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.Plugin

class InsectItemStackConverter(
    plugin: Plugin,
    private val insectTypeTable: InsectTypeTable
) {
    private val insectReader: InsectItemTagReader
    private val insectWriter: InsectItemTagWriter

    init {
        val insectRarityKey = NamespacedKey(plugin, "insectRarity")
        val insectLengthKey = NamespacedKey(plugin, "insectLength")
        insectWriter = InsectItemTagWriter(insectRarityKey, insectLengthKey)
        insectReader = InsectItemTagReader(insectTypeTable, insectRarityKey, insectLengthKey)
    }

    /**
     * アイテムが昆虫かどうかを確認するメソッド
     *
     * @return 昆虫ならtrueを返します
     */
    fun isInsect(itemStack: ItemStack): Boolean {
        val meta = itemStack.itemMeta ?: throw IllegalStateException()
        return insectReader.canRead(meta)
    }

    /**
     * 昆虫のレアリティと大きさを取得するメソッド
     *
     * @return レアリティと大きさを返します
     */
    fun insect(itemStack: ItemStack): Insect {
        val meta = itemStack.itemMeta
            ?: throw IllegalStateException()
        return insectReader.read(meta)
    }

    /**
     * 虫のアイテムを作成するメソッド
     *
     * @return 額縁にがっちゃんこするためのアイテムを返します
     */
    fun createItemStack(catcher: Player, insect: Insect): ItemStack {
        val insectType = insect.type
        val material = Material.matchMaterial(insectType.icon.id)
            ?: throw IllegalStateException("${insectType.icon.id} というアイテムは存在しません")
        val itemStack = ItemStack(material, 1)

        itemStack.edit<ItemMeta> {
            displayName(
                Component
                    .text(insectType.name)
                    .color(insectTypeTable.rarityMap[insectType.rarity]?.let { TextColor.fromCSSHexString(it.color) })
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.ITALIC, false)
            )
            // TODO: 説明文のリプレイス
            lore(insectTypeTable.itemFormat.description.plus(insectType.icon.comment.orEmpty()).toComponent())
            setCustomModelData(insectType.icon.customModelData)
            insectType.icon.enchantments?.let {
                for ((enchantment, level) in generateEnchantments(insectType.icon.enchantments)) {
                    addEnchant(enchantment, level, true)
                }
            }
            insectWriter.write(this, insect)
        }

        insectType.icon.skullTexture?.let {
            itemStack.editIfHas<SkullMeta> {
                val insectProfile = Bukkit.createProfile("insect")
                insectProfile.setProperty(ProfileProperty("textures", insectType.icon.skullTexture))
                playerProfile = insectProfile
            }
        }
        return itemStack
    }

    private fun generateEnchantments(enchantments: List<String>): Map<Enchantment, Int> {
        return enchantments.map {
            val tokens = it.split(DELIMITER)
            val id = NamespacedKey.minecraft(tokens[0])
            val enchantment: Enchantment = Enchantment.getByKey(id)
                ?: throw IllegalStateException("IDが${id}のエンチャントは存在しません")
            val level = tokens[1].toInt()
            Pair(enchantment, level)
        }.toMap()
    }

    companion object {
        private const val DELIMITER = '|'
    }
}
