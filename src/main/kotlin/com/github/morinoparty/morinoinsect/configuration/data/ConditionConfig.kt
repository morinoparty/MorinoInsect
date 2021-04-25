package com.github.morinoparty.morinoinsect.configuration.data

import kotlinx.serialization.Serializable

/**
 * 虫のコンディションデータクラス
 *
 * @constructor 虫のコンディション一覧
 * @param blocks スポーン可能なブロック (例：stone,dark_oak_log)
 * @param directions スポーンする向き (例：up|down)
 * @param time スポーンする時間　(day or night)
 * @author うにたろう
 */
@Serializable
data class ConditionConfig(
    val blocks: List<String> = emptyList(),
    val directions: List<String> = emptyList(),
    val time: String? = null,
    val biomes: List<String>? = null,
    val locationY: String? = null,
    val raining: Boolean? = null,
    val thundering: Boolean? = null
)
