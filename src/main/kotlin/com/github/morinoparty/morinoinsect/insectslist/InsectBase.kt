package com.github.morinoparty.morinoinsect.insectslist

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.condition.SpawnTypeCondition
import com.github.morinoparty.morinoinsect.condition.SpawnableBlockCondition
import com.github.morinoparty.morinoinsect.extentions.selectOneRandomly
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

/** 虫クラスを作るにあたってのベースになるインターフェース */
interface InsectBase {

    /** 虫のレアリティ */
    val reality: InsectTier

    /** 虫の名前 */
    val displayName: String

    /** 虫のID */
    val insectId: Int

    /** 虫のサイズの最小値 */
    val lengthMin: Int

    /** 虫のサイズの最大値 */
    val lengthMax: Int

    /** 虫がスポーンできるブロック */
    val spawnableBlock: SpawnableBlockCondition

    /**
     * スポーンタイプ
     * どういうスポーンの仕方をするかを指定する
     */
    val spawnType: SpawnTypeCondition

    /** 虫の見た目 */
    val icon: ItemStack

    /**
     * 虫を指定したブロックの位置に透明の額縁に入れて指定したスポーンタイプでスポーンさせる
     *
     * @param[player] 対象のプレイヤー
     * @param[spawnBlock] スポーンブロック
     */
    fun spawn(player: Player, spawnBlock: Block) {
        if (spawnType.checkCondition(SpawnType.ONGROUND)) {
            val itemFrame = player.world.spawn(spawnBlock.location, ItemFrame::class.java)
            // 額縁を透明にして壊れないようにする
            val invisible = NamespacedKey(MorinoInsect.getInstance(), "invisible")
            val invulnerable = NamespacedKey(MorinoInsect.getInstance(), "invulnerable")
            val itemFrameDirection = BlockFace.DOWN
            itemFrame.persistentDataContainer.set(invisible, PersistentDataType.BYTE, 1)
            itemFrame.persistentDataContainer.set(invulnerable, PersistentDataType.BYTE, 1)
            itemFrame.setFacingDirection(itemFrameDirection)
            itemFrame.setItem(icon)
        } else if (spawnType.checkCondition(SpawnType.ONTREE)) {
            val itemFrame = player.world.spawn(spawnBlock.location, ItemFrame::class.java)
            // 額縁を透明にして壊れないようにする
            val invisible = NamespacedKey(MorinoInsect.getInstance(), "invisible")
            val invulnerable = NamespacedKey(MorinoInsect.getInstance(), "invulnerable")
            val itemFrameDirection = getUnburiedDirection(spawnBlock)
            itemFrame.persistentDataContainer.set(invisible, PersistentDataType.BYTE, 1)
            itemFrame.persistentDataContainer.set(invulnerable, PersistentDataType.BYTE, 1)
            if (itemFrameDirection == null) return
            itemFrame.setFacingDirection(itemFrameDirection)
        }
    }

    /**
     * 指定したスポーンブロックの他のブロックに埋もれてない面を検出して、そこからランダムに１つの面を選ぶ
     *
     * @param[spawnBlock] 対象のスポーンブロック
     * @return スポーンブロックの他のブロックに埋もれてない面からランダムに選んだ１つの面
     */
    private fun getUnburiedDirection(spawnBlock: Block): BlockFace {
        val unburiedDirection: MutableList<BlockFace> = ArrayList()
        val northBlockNextTo = spawnBlock.getRelative(BlockFace.NORTH)
        val southBlockNextTo = spawnBlock.getRelative(BlockFace.SOUTH)
        val eastBlockNextTo = spawnBlock.getRelative(BlockFace.EAST)
        val westBlockNextTo = spawnBlock.getRelative(BlockFace.WEST)
        val isNorthBlockAir = northBlockNextTo.type == Material.AIR
        val isSouthBlockAir = southBlockNextTo.type == Material.AIR
        val isEastBlockAir = eastBlockNextTo.type == Material.AIR
        val isWestBlockAir = westBlockNextTo.type == Material.AIR
        when {
            isNorthBlockAir == true -> { unburiedDirection.add(BlockFace.NORTH) }
            isSouthBlockAir == true -> { unburiedDirection.add(BlockFace.SOUTH) }
            isEastBlockAir == true -> { unburiedDirection.add(BlockFace.EAST) }
            isWestBlockAir == true -> { unburiedDirection.add(BlockFace.WEST) }
        }
        return unburiedDirection.selectOneRandomly()
    }
}
