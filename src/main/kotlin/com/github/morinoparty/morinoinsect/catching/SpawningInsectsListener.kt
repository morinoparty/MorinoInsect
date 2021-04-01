package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.catching.area.AreaChecker
import com.github.morinoparty.morinoinsect.catching.area.Vector
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
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
        spawnInsect(player, spawnBlock)
    }

    // 虫をランダムに選んでスポーンさせるメソッド
    private fun spawnInsect(catcher: Player, spawnBlock: Block) {
        val insect: Insect = plugin.insectTypeTable.pickRandomType(
            catcher = catcher,
            block = spawnBlock.type
        )?.generateInsect() ?: throw IllegalStateException("条件に当てはまる虫はありませんでした")
        val insectItem = plugin.converter.createItemStack(catcher, insect)
        val insectItemFrame: ItemFrame =
            catcher.world.spawnEntity(spawnBlock.location, EntityType.ITEM_FRAME) as ItemFrame
        insectItemFrame.setItem(insectItem)

        // 透明化させるために一旦はItemFrameにキャストした物を後でLivingEntityにキャストしている
        insectItemFrame as LivingEntity
        insectItemFrame.isInvisible = true
    }

    // プレイヤーの周り（半径３ブロックの円）にあるブロックを検知して返すメソッド
    private fun detectBlocksAroundPlayer(player: Player): MutableList<Block> {
        // プレイヤーの真下のブロックの位置を取得したいため、y座標を-1した
        val playerLocation = player.location.subtract(0.0, 1.0, 0.0)
        val detectingBlocksArea = Vector(3, 3, 3)
        val detectedBlocks: MutableList<Block> = mutableListOf()
        // 指定した範囲にあるブロックを検知してListに一つずつ入れていく
        for (x in 0..detectingBlocksArea.x step 1) {
            for (z in 0..detectingBlocksArea.z step 1) {
                for (y in 0..detectingBlocksArea.y step 1) {
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
