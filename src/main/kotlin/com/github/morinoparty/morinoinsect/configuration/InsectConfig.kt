package com.github.morinoparty.morinoinsect.configuration

import com.github.morinoparty.morinoinsect.catching.insect.InsectCatchingNet
import com.github.morinoparty.morinoinsect.catching.insect.InsectRarity
import com.github.morinoparty.morinoinsect.catching.insect.InsectType
import com.github.morinoparty.morinoinsect.catching.insect.ItemFormat
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
data class InsectConfig(
    val itemFormat: ItemFormat = ItemFormat(),
    val insectCatchingNet: InsectCatchingNet = InsectCatchingNet(),
    val rarityMap: Map<String, InsectRarity> = emptyMap(),
    val insectMap: Map<String, InsectType> = emptyMap()
)
