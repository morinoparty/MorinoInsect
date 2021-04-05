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
     * @param spawnType 虫のスポーンする向き
     * @return チェックに合格すればtrueを返します
     */
    fun check(
        catcher: Player,
        block: Block,
        spawnType: BlockFace
    ): Boolean
}
