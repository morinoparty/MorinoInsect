package com.github.morinoparty.morinoinsect.configuration.data

import kotlinx.serialization.Serializable

/**
 * ゲットする虫の形式を決定するデータクラス
 *
 * @param lore 全ての虫につけるの説明文
 */
@Serializable
data class ItemFormatConfig(
    val lore: List<String> = emptyList()
)
