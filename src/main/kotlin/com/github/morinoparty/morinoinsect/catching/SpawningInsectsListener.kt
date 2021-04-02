package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.area.AreaChecker
import com.github.morinoparty.morinoinsect.catching.area.Vector
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.lang.IllegalStateException

/** 虫のスポーン処理をするイベントクラス */
class SpawningInsectsListener(
    private val plugin: MorinoInsect,
    private val catchingNet: ItemStack
) : Listener {

    /**
     * プレイヤーが虫網を右クリックするのを感知して虫をスポーンさせるメソッド
     *
     * @param event プレイヤーがエンティティやブロックをクリックしたのを感知するイベント
     */
    @EventHandler
    fun onSpawn(event: PlayerInteractEvent) {
        val player = event.player
        val playerHasInsectCatchingNet =
            player.inventory.itemInMainHand == catchingNet
        if (!playerHasInsectCatchingNet) return
        val spawnBlock = detectBlocksAroundPlayer(player).random()
        val randomSpawnType = BlockFace.values().random()
        spawnInsect(player, spawnBlock, randomSpawnType)
    }

    // 虫をランダムに選んでスポーンさせるメソッド
    private fun spawnInsect(@NotNull catcher: Player, spawnBlock: Block, spawnType: BlockFace) {
        val insect: Insect = plugin.insectTypeTable.pickRandomType(
            catcher = catcher,
            block = spawnBlock,
            spawnType = spawnType
        )?.generateInsect() ?: throw IllegalStateException("条件に当てはまる虫はありませんでした")
        val insectItem = plugin.converter.createItemStack(catcher, insect)
        val insectItemFrame: ItemFrame =
            catcher.world.spawnEntity(spawnBlock.location, EntityType.ITEM_FRAME) as ItemFrame
        insectItemFrame.also {
            it.setItem(insectItem)
            it.setFacingDirection(spawnType)
        }

        // 透明化させるために一旦はItemFrameにキャストした物を後でLivingEntityにキャストしている
        insectItemFrame as LivingEntity
        insectItemFrame.isInvisible = true
    }

    // プレイヤーの周り（半径３ブロックの円）にあるブロックを検知して返すメソッド
    @Nullable
    private fun detectBlocksAroundPlayer(@NotNull player: Player): MutableList<Block> {

        // プレイヤーの真下のブロックの位置を取得したいため、y座標を-1した
        val playerLocation = player.location.subtract(0.0, 1.0, 0.0)
        val leftDetectArea = Vector(3, 3, 3)
        val rightDetectArea = Vector(-3, 3, -3)
        val detectedBlocks: MutableList<Block> = mutableListOf()

        // 全体の検出範囲の左側を検出して検知したブロックをListに入れる
        for (x in 0..leftDetectArea.x step 1) {
            for (z in 0..leftDetectArea.z step 1) {
                for (y in 0..leftDetectArea.y step 1) {
                    val areaChecker = AreaChecker(x, z)
                    if (areaChecker.isSquareCorner()) continue
                    val detectedLocation = playerLocation.add(x.toDouble(), y.toDouble(), z.toDouble())
                    detectedBlocks.add(detectedLocation.block)
                }
            }
        }

        // 全体の検出範囲の右側を検出して検知したブロックをListに入れる
        for (x in 0..rightDetectArea.x step -1) {
            for (z in 0..rightDetectArea.z step -1) {
                for (y in 0..rightDetectArea.y step 1) {
                    val areaChecker = AreaChecker(x, z)
                    if (areaChecker.isSquareCorner()) continue
                    val detectedLocation = playerLocation.add(x.toDouble(), y.toDouble(), z.toDouble())
                    detectedBlocks.add(detectedLocation.block)
                }
            }
        }
        return detectedBlocks
    }
}
