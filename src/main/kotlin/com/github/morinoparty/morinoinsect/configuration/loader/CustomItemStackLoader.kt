package com.github.morinoparty.morinoinsect.configuration.loader

import com.destroystokyo.paper.profile.ProfileProperty
import com.github.morinoparty.morinoinsect.configuration.data.IconConfig
import com.github.morinoparty.morinoinsect.util.edit
import com.github.morinoparty.morinoinsect.util.editIfHas
import com.github.morinoparty.morinoinsect.util.miniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

class CustomItemStackLoader(
    private val enchantmentMapLoader: EnchantmentMapLoader
) {
    fun loadFrom(icon: IconConfig): ItemStack {
        val material = Material.matchMaterial(icon.id)
            ?: throw IllegalStateException("${icon.id} というアイテムは存在しません")
        val itemStack = ItemStack(material, 1)

        itemStack.edit<ItemMeta> {
            displayName(icon.displayName.miniMessage())
            lore(icon.lore.miniMessage())
            setCustomModelData(icon.customModelData)
            icon.enchantments?.let {
                for ((enchantment, level) in enchantmentMapLoader.loadFrom(icon.enchantments)) {
                    addEnchant(enchantment, level, true)
                }
            }
        }

        icon.skullTexture?.let {
            itemStack.editIfHas<SkullMeta> {
                val insectProfile = Bukkit.createProfile("insect")
                insectProfile.setProperty(ProfileProperty("textures", icon.skullTexture))
                playerProfile = insectProfile
            }
        }
        return itemStack
    }
}
