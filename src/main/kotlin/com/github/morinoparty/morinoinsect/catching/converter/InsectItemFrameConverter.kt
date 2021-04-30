package com.github.morinoparty.morinoinsect.catching.converter

import br.com.devsrsouza.kotlinbukkitapi.extensions.server.worlds
import com.github.morinoparty.morinoinsect.catching.tag.InsectFrameTagManager
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Rotation
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemFrame
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class InsectItemFrameConverter(plugin: Plugin) {
    private val frameTagConverter: InsectFrameTagManager

    init {
        val frameTagKey = NamespacedKey(plugin, "spawnType")
        frameTagConverter = InsectFrameTagManager(frameTagKey)
    }

    fun isInsectFrame(entity: Entity) = frameTagConverter.canRead(entity)

    fun loadTag(itemFrame: ItemFrame) = frameTagConverter.read(itemFrame)

    fun spawn(block: Block, insectItem: ItemStack, face: BlockFace) = spawn(block, insectItem, face, SPAWN)

    fun place(block: Block, insectItem: ItemStack, face: BlockFace) = spawn(block, insectItem, face, PLACE)

    fun spawnOnAir(block: Block, insectItem: ItemStack, face: BlockFace): ItemFrame? {
        return if (block.type.isAir) {
            block.type = Material.BARRIER
            val spawn = spawn(block, insectItem, face)
            block.type = Material.AIR
            spawn
        } else null
    }

    fun killAll() {
        val entities = mutableListOf<Entity>()
        for (world in worlds) entities.addAll(world.entities)
        entities.filter { it is ItemFrame && isInsectFrame(it) && loadTag(it) == SPAWN }
            .forEach { it.remove() }
    }

    private fun spawn(block: Block, insectItem: ItemStack, face: BlockFace, tag: Byte): ItemFrame {
        val spawnLocation = block.location.adjustSpawnLocation(face)
            ?: throw IllegalStateException("有効な設置面が見つかりません")
        return block.world.spawn(spawnLocation, ItemFrame::class.java) { itemFrame ->
            // itemFrame.setFacingDirection(face, false) // TODO: 向きを設定するとタグが消えるバグ
            itemFrame.isVisible = false
            // itemFrame.isFixed = true
            itemFrame.setItem(insectItem)
            itemFrame.rotation = Rotation.values().random()
            frameTagConverter.write(itemFrame, tag)
        }
    }

    private fun Location.adjustSpawnLocation(face: BlockFace): Location? {
        return when (face) {
            BlockFace.UP -> this.add(0.0, 1.0, 0.0)
            BlockFace.DOWN -> this.add(0.0, -1.0, 0.0)
            BlockFace.SOUTH -> this.add(0.0, 0.0, 1.0)
            BlockFace.NORTH -> this.add(0.0, 0.0, -1.0)
            BlockFace.EAST -> this.add(1.0, 0.0, 0.0)
            BlockFace.WEST -> this.add(-1.0, 0.0, 0.0)
            else -> null
        }
    }

    companion object {
        private const val SPAWN: Byte = 1
        private const val PLACE: Byte = 0
    }
}
