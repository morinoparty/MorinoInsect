package com.github.morinoparty.morinoinsect.configuration

import com.github.morinoparty.morinoinsect.configuration.data.IconConfig
import com.github.morinoparty.morinoinsect.configuration.data.InsectRarityConfig
import com.github.morinoparty.morinoinsect.configuration.data.InsectTypeConfig
import com.github.morinoparty.morinoinsect.configuration.data.ItemFormatConfig
import kotlinx.serialization.Serializable

/**
 * 虫一覧やレアリティ一覧を格納するデータクラス
 *
 * @param itemFormat アイテムの基本説明文
 * @param rarityList レアリティのリスト
 * @param insectList 虫のリスト
 * @author うにたろう
 */
@Serializable
data class InsectConfig(
    val itemFormat: ItemFormatConfig = ItemFormatConfig(),
    val insectNet: IconConfig = IconConfig(),
    val rarityList: Map<String, InsectRarityConfig> = mapOf(),
    val insectList: Map<String, InsectTypeConfig> = mapOf()
)
