package com.github.morinoparty.morinoinsect.configuration.data

import kotlinx.serialization.Serializable

/**
 * アイテムのデータクラス
 *
 * @param id アイテムのMinecraftID
 * @param customModelData テクスチャの番号
 * @param skullTexture プレイヤーヘッドの場合に使うテクスチャ情報
 * @param enchantments 追加するエンチャント
 */
@Serializable
data class IconConfig(
    val id: String = "",
    val displayName: String = "",
    val customModelData: Int? = null,
    val skullTexture: String? = null,
    val enchantments: List<String>? = null,
    val lore: List<String> = emptyList()
)
