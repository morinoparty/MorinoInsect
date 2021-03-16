package com.github.morinoparty.morinoinsect.condition

import org.bukkit.Material

/**
 * 虫がスポーン可能なブロックかを確認するコンディションクラス
 *
 * @property block スポーンブロックに指定したいブロックのマテリアル
 */
class SpawnableBlockCondition(private val block: Material) : Condition {

    /**
     * クラスのプロパティで指定したブロックを元に、虫がスポーン可能なブロックかを確認するメソッド
     *
     * @return スポーン可能なブロックならばtrue,そうじゃなければfalse
     */
    override fun <T> checkCondition(targetValue: T): Boolean {
        val targetBlock = targetValue as Material
        return targetBlock == block
    }
}
