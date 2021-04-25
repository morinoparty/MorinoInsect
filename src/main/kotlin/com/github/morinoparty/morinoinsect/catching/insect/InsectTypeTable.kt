package com.github.morinoparty.morinoinsect.catching.insect

import org.bukkit.Material
import org.bukkit.entity.Player

interface InsectTypeTable : Map<InsectRarity, Set<InsectType>> {
    val rarities: Set<InsectRarity>
    val types: Set<InsectType>

    fun pickRandomRarity(): InsectRarity

    /**
     * 虫のリストからランダムに一匹を生成する。レアリティをランダムに決定し、レアリティに合う虫を選別し、コンディションをチェックして、ランダムに一匹を選びます。
     *
     * @param catcher 虫をゲットするプレイヤー
     * @param material スポーン可能なブロック
     * @return スポーン可能な虫がいない時はnullを返します
     */
    fun pickRandomType(
        catcher: Player,
        material: Material,
        rarity: InsectRarity = pickRandomRarity(),
        spawnDirection: SpawnDirection
    ): InsectType?
}
