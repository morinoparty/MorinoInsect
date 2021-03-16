package com.github.morinoparty.morinoinsect.condition

import com.github.morinoparty.morinoinsect.insectslist.SpawnType

/**
 * 虫のスポーンタイプが適切なものになっているか確認するコンディションクラス
 *
 * @property spawnType 虫のスポーンタイプ
 */
class SpawnTypeCondition(private val spawnType: SpawnType) : Condition {

    /**
     * 虫のスポーンタイプが適切なものになっているか確認するメソッド
     *
     * @param anyValue スポーンタイプ
     * @return スポーンタイプが適切なものになっていればtrue,なっていなければfalse
     */
    override fun <T> checkCondition(targetValue: T): Boolean {
        val spawnType = targetValue as SpawnType
        return spawnType == this.spawnType
    }
}
