package com.github.morinoparty.morinoinsect.catching.condition

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * 虫のコンディションインターフェース
 * チェックするコンディションを増やす時に使ってください
 *
 * @author うにたろう
 */
interface Condition {
    /**
     * 必要なプロパティを受け取りコンディションチェックを行います
     *
     * @param catcher 虫を捕まえるプレイヤー
     * @param block スポーンさせるブロック
     * @return チェックに合格すればtrueを返します
     */
    fun check(
        catcher: Player,
        block: Block,
        spawnType: BlockFace
        // ToDo: [東、西、北、南、上、下]の６つのタイプから成るspawnTypeを作り、指定したスポーンタイプの隣がブロックで埋もれてないかも同時にチェックする
    ): Boolean
}
