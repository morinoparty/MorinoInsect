package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.catching.area.SpawnType
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.random.Random

class InsectTypeTable(
    val rarityMap: Map<String, InsectRarity>,
    val insectMap: Map<String, InsectType>
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
        spawnType: SpawnType
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
