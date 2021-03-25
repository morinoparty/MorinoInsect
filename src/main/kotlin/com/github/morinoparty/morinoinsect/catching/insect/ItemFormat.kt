package com.github.morinoparty.morinoinsect.catching.insect

import kotlinx.serialization.Serializable

/**
 * ゲットする虫の形式を決定するデータクラス
 *
 * @param description 全ての虫につけるの説明文
 */
@Serializable
data class ItemFormat(
    val description: List<String> = emptyList()
)