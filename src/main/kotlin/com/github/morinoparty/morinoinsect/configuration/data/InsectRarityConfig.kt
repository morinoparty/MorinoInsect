package com.github.morinoparty.morinoinsect.configuration.data

import kotlinx.serialization.Serializable

/**
 * 虫のレアリティデータクラス
 *
 * @constructor 虫のレアリティに付随する様々な設定値
 * @param displayName 名前
 * @param color 色
 * @param chance そのレアリティの虫が発生する確率
 * @param catchAnnounce 虫をゲットした時に送信するメッセージの種類
 * @author うにたろう
 */
@Serializable
data class InsectRarityConfig(
    val displayName: String = "",
    val color: String = "",
    val chance: Double = 0.0,
    val catchAnnounce: Int = 0,
    val firework: Int? = null,
    val commands: List<String>? = null
)
