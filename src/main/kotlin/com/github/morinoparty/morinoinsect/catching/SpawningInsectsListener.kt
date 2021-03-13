package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.extentions.selectRandomly
import com.github.morinoparty.morinoinsect.insectslist.InsectsManager
import com.github.morinoparty.morinoinsect.util.Vector
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.collections.ArrayList

class SpawningInsectsListener(private val plugin: MorinoInsect) : Listener {

    private val insectsManager = InsectsManager()
    private val walkCount = mutableMapOf<Player, Int?>()
    private val walkCountMax = 30
    private val appropriateBlocks: MutableList<Block> = ArrayList()
    private val randomlySelectedInsects = insectsManager.selectInsectRandomly()

    @EventHandler
    fun onSpawn(event: PlayerMoveEvent) {
        val player = event.player
        if (!isPlayerInPlayerWalkCountMap(player)) {
            walkCount[player] = 0
        } else {
            var playerWalkCount = walkCount[player]?.plus(1)
            setPlayerWalkCount(player, playerWalkCount)
            // ３０ブロック歩いたら周りに虫をスポーンさせる
            if (playerWalkCount == null) return
            if (playerWalkCount < walkCountMax) return
            detectBlocksAroundPlayer(player)
            if (!isDetectedBlocksEmpty(player)) return
            // chooseSpawnBlock()で返される配列には２個しか要素が入っていないかつ、入れる場所を指定しているので、下のような処理が可能
            val randomSpawnBlock1 = chooseSpawnBlock()[0]
            val randomSpawnBlock2 = chooseSpawnBlock()[1]
            spawnInsect1(player, randomSpawnBlock1)
            spawnInsect2(player, randomSpawnBlock2)
            resetBlocksLists()
            resetPlayerWalkCount(player)
        }
    }

    // 検知されたブロックのリストに指定されたブロックが入っていなかった場合に
    // walkCountをリセットする処理をわざわざonSpawnメソッドに書きたくなかったからここに書いた
    private fun isDetectedBlocksEmpty(player: Player): Boolean {
        if (appropriateBlocks.count() == 0) {
            resetPlayerWalkCount(player)
            player.sendMessage("虫は居ないようだ...")
            return false
        } else {
            return true
        }
    }

    private fun resetBlocksLists() {
        appropriateBlocks.clear()
    }

    // ToDo: insect.ymlコンフィグから取得したspawnableBlockを元に検知したいブロックのリストを作成する
    private fun chooseAppropriateBlock(targetBlock: Block) {
        // randomlySelectedInsects配列には２個しか要素が入っていないかつ、入れる場所を指定しているので、下のような処理が可能
        val condition1 = randomlySelectedInsects[0].spawnableBlock == (targetBlock.type)
        val condition2 = randomlySelectedInsects[1].spawnableBlock == (targetBlock.type)
        if (condition1 || condition2) {
            appropriateBlocks.add(targetBlock)
        }
    }

    private fun setPlayerWalkCount(player: Player, playerWalkCount: Int?) {
        walkCount[player] = playerWalkCount
    }

    private fun resetPlayerWalkCount(player: Player) {
        walkCount[player] = 0
    }

    private fun isPlayerInPlayerWalkCountMap(player: Player): Boolean {
        return walkCount.containsKey(player)
    }

    // ToDo: detectBlocksAroundPlayerの検知範囲が間違っていたから修正する必要あり
    //       実際には半径３ブロックの円のような範囲になる
    private fun detectBlocksAroundPlayer(player: Player) {
        // プレイヤーの真下のブロックの位置を取得したいため、y座標を-1した
        val playerLocation = player.location.subtract(0.0, 1.0, 0.0)
        val detectingBlocksArea = Vector(6, 3, 6)
        // 指定した範囲にあるブロックを検知してListに一つずつ入れていく
        for (x in 0..detectingBlocksArea.x step 1) {
            for (z in 0..detectingBlocksArea.z step 1) {
                for (y in 0..detectingBlocksArea.y step 1) {
                    val detectedArea = playerLocation.add(x.toDouble(), y.toDouble(), z.toDouble())
                    val detectedBlock = detectedArea.block
                    chooseAppropriateBlock(detectedBlock)
                }
            }
        }
    }

    // 二つの無造作に選ばれたブロックに二つの無造作に選ばれた虫をスポーンさせる処理を書くためメソッドが２つ必要になった
    private fun spawnInsect1(player: Player, spawnBlock: Block) {
        val insect1 = randomlySelectedInsects[0]
        insect1.spawn(player, spawnBlock)
    }

    private fun spawnInsect2(player: Player, spawnBlock: Block) {
        val insect2 = randomlySelectedInsects[1]
        insect2.spawn(player, spawnBlock)
    }

    private fun chooseSpawnBlock(): MutableList<Block> {
        return appropriateBlocks.selectRandomly()
    }
}
