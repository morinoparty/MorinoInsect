package com.github.morinoparty.morinoinsect.catching.insect

import kotlinx.serialization.Serializable

/**
 * 虫のコンディションデータクラス
 *
 * @constructor 虫のコンディション一覧
 * @param blocks スポーン可能なブロック (例：stone,dark_oak_log)
 * @param direction スポーンする位置 (例：tree|ground)
 * @param time スポーンする時間　(day or night)
 * @author うにたろう
 */
@Serializable
data class InsectCondition(
    val blocks: List<String> = emptyList(),
    val direction: String = "",
    val time: String? = null
)
