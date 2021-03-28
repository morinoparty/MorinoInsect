package com.github.morinoparty.morinoinsect.catching.insect

import kotlinx.serialization.Serializable

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
)
