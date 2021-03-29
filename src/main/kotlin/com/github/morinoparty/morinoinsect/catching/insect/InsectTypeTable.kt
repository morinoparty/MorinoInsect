package com.github.morinoparty.morinoinsect.catching.insect

import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.random.Random

/**
 * 虫一覧やレアリティ一覧を格納するデータクラス
 *
 * @param itemFormat アイテムの基本説明文
 * @param rarityMap レアリティのリスト
 * @param insectMap 虫のリスト
 * @author うにたろう
 */
@Serializable
data class InsectTypeTable(
    val itemFormat: ItemFormat = ItemFormat(),
    val rarityMap: Map<String, InsectRarity> = emptyMap(),
    val insectMap: Map<String, InsectType> = emptyMap()
) {
    /**
     * 虫のリストからランダムに一匹を生成する。レアリティをランダムに決定し、レアリティに合う虫を選別し、コンディションをチェックして、ランダムに一匹を選びます。
     *
     * @param catcher 虫をゲットするプレイヤー
     * @param block スポーン可能なブロック
     * @return スポーン可能な虫がいない時はnullを返します
     */
    fun pickRandomType(
        catcher: Player,
        block: Material,
        spawnType: String
        // TODO: ディレクションチェックのために引数が増える予定
    ): InsectType? {
        val types = insectMap.values.filter {
            it.rarity == pickRandomRarity().first && it.conditions.generateConditionSet()
                .all { condition -> condition.check(catcher, block, spawnType) }
        }
        if (types.isEmpty()) return null
        return types.random()
    }

    private fun pickRandomRarity(): Pair<String, InsectRarity> {
        val completeWeight = rarityMap.values.sumByDouble {
            it.chance
        }
        val random = Random.nextDouble() * completeWeight
        var countWeight = 0.0
        for (rarity in rarityMap) {
            countWeight += rarity.value.chance
            if (countWeight >= random) return rarity.toPair()
        }
        return rarityMap.maxByOrNull { it.value.chance }?.toPair()
            ?: throw IllegalStateException("レアリティを追加して下さい")
    }
}
