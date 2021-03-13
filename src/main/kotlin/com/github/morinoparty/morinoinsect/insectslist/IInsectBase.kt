package com.github.morinoparty.morinoinsect.insectslist

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.extentions.selectOneRandomly
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

interface IInsectBase {

    val reality: InsectTier
    val displayName: String
    val insectId: Int
    val lengthMin: Int
    val lengthMax: Int
    val spawnableBlock: Material
    val spawnType: SpawnType
    val icon: ItemStack

    fun spawn(player: Player, spawnBlock: Block) {
        if (spawnType == SpawnType.ONGROUND) {
            val itemFrame = player.world.spawn(spawnBlock.location, ItemFrame::class.java)
            // 額縁を透明にして壊れないようにする
            val invisible = NamespacedKey(MorinoInsect.getInstance(), "invisible")
            val invulnerable = NamespacedKey(MorinoInsect.getInstance(), "invulnerable")
            itemFrame.persistentDataContainer.set(invisible, PersistentDataType.BYTE, 1)
            itemFrame.persistentDataContainer.set(invulnerable, PersistentDataType.BYTE, 1)
            itemFrame.setItem(icon)
            val itemFrameDirection = BlockFace.DOWN
            itemFrame.setFacingDirection(itemFrameDirection)
        } else if (spawnType == SpawnType.ONTREE) {
            val itemFrame = player.world.spawn(spawnBlock.location, ItemFrame::class.java)
            // 額縁を透明にして壊れないようにする
            val invisible = NamespacedKey(MorinoInsect.getInstance(), "invisible")
            val invulnerable = NamespacedKey(MorinoInsect.getInstance(), "invulnerable")
            itemFrame.persistentDataContainer.set(invisible, PersistentDataType.BYTE, 1)
            itemFrame.persistentDataContainer.set(invulnerable, PersistentDataType.BYTE, 1)
            val itemFrameDirection = getAppropriateDirection(spawnBlock)
            if (itemFrameDirection == null) return
            itemFrame.setFacingDirection(itemFrameDirection)
        }
    }

    private fun getAppropriateDirection(spawnBlock: Block): BlockFace {
        val appropriateDirection: MutableList<BlockFace> = ArrayList()
        val northBlockNextTo = spawnBlock.getRelative(BlockFace.NORTH)
        val southBlockNextTo = spawnBlock.getRelative(BlockFace.SOUTH)
        val eastBlockNextTo = spawnBlock.getRelative(BlockFace.EAST)
        val westBlockNextTo = spawnBlock.getRelative(BlockFace.WEST)
        val isNorthBlockAir = northBlockNextTo.type == Material.AIR
        val isSouthBlockAir = southBlockNextTo.type == Material.AIR
        val isEastBlockAir = eastBlockNextTo.type == Material.AIR
        val isWestBlockAir = westBlockNextTo.type == Material.AIR
        when {
            isNorthBlockAir == true -> { appropriateDirection.add(BlockFace.NORTH) }
            isSouthBlockAir == true -> { appropriateDirection.add(BlockFace.SOUTH) }
            isEastBlockAir == true -> { appropriateDirection.add(BlockFace.EAST) }
            isWestBlockAir == true -> { appropriateDirection.add(BlockFace.WEST) }
        }
        return appropriateDirection.selectOneRandomly()
    }
}
