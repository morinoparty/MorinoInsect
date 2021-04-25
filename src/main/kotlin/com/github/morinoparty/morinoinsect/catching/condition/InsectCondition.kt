package com.github.morinoparty.morinoinsect.catching.condition

import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * 虫のコンディションインターフェース
 * チェックするコンディションを増やす時に使ってください
 *
 * @author うにたろう
 */
interface InsectCondition {
    /**
     * 必要なプロパティを受け取りコンディションチェックを行います
     *
     * @param catcher 虫を捕まえるプレイヤー
     * @param material スポーンさせるブロック
     * @param spawnDirection 虫のスポーンする向き
     * @return チェックに合格すればtrueを返します
     */
    fun check(
        catcher: Player,
        material: Material,
        spawnDirection: SpawnDirection
    ): Boolean
}
