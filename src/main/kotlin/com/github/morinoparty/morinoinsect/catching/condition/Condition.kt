package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.SpawnType
import org.bukkit.Material
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
        block: Material
    ): Boolean
}
