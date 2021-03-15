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

/** 虫のスポーン処理をするイベントクラス */
class SpawningInsectsListener(private val plugin: MorinoInsect) : Listener {

    private val insectsManager = InsectsManager()

    /** プレイヤーが歩いた歩数を保存しておくマップ */
    private val walkCount = mutableMapOf<Player, Int?>()

    /** プレイヤーの歩数上限 */
    private val walkCountMax = 30

    /** 虫がスポーン可能な適切なブロックを入れるリスト */
    private val appropriateBlocks: MutableList<Block> = ArrayList()

    private val randomlySelectedInsects = insectsManager.selectInsectRandomly()

    /**
     * プレイヤーが歩くのを感知して、３０歩歩くと周りに虫をスポーンさせる
     *
     * @param event プレイヤーの動きを感知するイベント
     */
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
    /**
     * 検知されたブロックが空かを確かめる
     *
     * @param player 対象プレイヤー
     * @return 空ならばfalse、要素が入っていればtrueを返す
     */
    private fun isDetectedBlocksEmpty(player: Player): Boolean {
        if (appropriateBlocks.count() == 0) {
            resetPlayerWalkCount(player)
            player.sendMessage("虫は居ないようだ...")
            return false
        } else {
            return true
        }
    }

    /** appropriateBlocksリストの中身を全部消す */
    private fun resetBlocksLists() {
        appropriateBlocks.clear()
    }

    /**
     * ランダムに選ばれた２匹の虫のどちらかに合う適切なブロックをappropriateBlocksリストに入れていく
     *
     * @param targetBlock 対象となるブロック
     */
    private fun chooseAppropriateBlock(targetBlock: Block) {
        // randomlySelectedInsects配列には２個しか要素が入っていないかつ、入れる場所を指定しているので、下のような処理が可能
        val condition1 = randomlySelectedInsects[0].spawnableBlock.checkCondition(targetBlock.type)
        val condition2 = randomlySelectedInsects[1].spawnableBlock.checkCondition(targetBlock.type)
        if (condition1 || condition2) {
            appropriateBlocks.add(targetBlock)
        }
    }

    /**
     * プレイヤーの歩数をwalkCountマップにセットする
     *
     * @param player 対象のプレイヤー
     * @param playerWalkCount 対象のプレイヤーにセットする歩数
     */
    private fun setPlayerWalkCount(player: Player, playerWalkCount: Int?) {
        walkCount[player] = playerWalkCount
    }

    /**
     * 指定したプレイヤーの歩数を０にする
     *
     * @param player 対象のプレイヤー
     */
    private fun resetPlayerWalkCount(player: Player) {
        walkCount[player] = 0
    }

    /**
     * 指定したプレイヤーがwalkCountに入っているか確かめる
     *
     * @param player 対象のプレイヤー
     * @return 入っていればtrue,入っていなければfalseを返す
     */
    private fun isPlayerInPlayerWalkCountMap(player: Player): Boolean {
        return walkCount.containsKey(player)
    }

    // ToDo: detectBlocksAroundPlayerの検知範囲が間違っていたから修正する必要あり
    //       実際には半径３ブロックの円のような範囲になる
    /**
     * プレイヤーの周りのブロックを検知して最終的には適切なブロックはappropriateBlocksリストに入れる
     *
     * @param player 対象のプレイヤー
     */
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
    /**
     * ランダムに選ばれた虫のうち１匹目のスポーン処理
     *
     * @param[player] 対象のプレイヤー
     * @param[spawnBlock] 虫がスポーンする対象になるブロック
     */
    private fun spawnInsect1(player: Player, spawnBlock: Block) {
        val insect1 = randomlySelectedInsects[0]
        insect1.spawn(player, spawnBlock)
    }

    /**
     * ランダムに選ばれた虫のうち２匹目のスポーン処理
     *
     * @param[player] 対象のプレイヤー
     * @param[spawnBlock] 虫がスポーンする対象になるブロック
     */
    private fun spawnInsect2(player: Player, spawnBlock: Block) {
        val insect2 = randomlySelectedInsects[1]
        insect2.spawn(player, spawnBlock)
    }

    /**
     * appropriateBlocksリストの中からランダムに２つの虫のスポーンの対象となるブロックを選ぶ
     *
     * @return ランダムに選ばれた２つのスポーンの対象となるブロックが入ったリスト
     */
    private fun chooseSpawnBlock(): MutableList<Block> {
        return appropriateBlocks.selectRandomly()
    }
}
