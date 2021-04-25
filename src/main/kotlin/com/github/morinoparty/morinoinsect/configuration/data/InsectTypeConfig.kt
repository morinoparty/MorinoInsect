package com.github.morinoparty.morinoinsect.configuration.data

import kotlinx.serialization.Serializable

/**
 * 虫の種類データクラス
 *
 * @constructor 虫のプロパティ
 * @param rarity レア度
 * @param lengthMin 最小サイズ
 * @param lengthMax 最大サイズ
 * @param conditions 発生条件
 * @param icon 内部情報
 * @author うにたろう
 */
@Serializable
data class InsectTypeConfig(
    val rarity: String = "",
    val lengthMin: Double = 0.0,
    val lengthMax: Double = 0.0,
    val commands: List<String>? = null,
    val conditions: ConditionConfig = ConditionConfig(),
    val icon: IconConfig = IconConfig(),
)
