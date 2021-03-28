package com.github.morinoparty.morinoinsect.catching.insect

import kotlinx.serialization.Serializable

/**
 * 虫の種類データクラス
 *
 * @constructor 虫のプロパティ
 * @param rarity レア度
 * @param name 虫の名前
 * @param lengthMin 最小サイズ
 * @param lengthMax 最大サイズ
 * @param conditions 発生条件
 * @param icon 内部情報
 * @author うにたろう
 */
@Serializable
data class InsectType(
    val rarity: String = "",
    val name: String = "",
    val lengthMin: Int = 0,
    val lengthMax: Int = 0,
    val conditions: InsectCondition = InsectCondition(),
    val icon: Icon = Icon()
) {
    /**
     * 虫の内部情報データクラス
     *
     * @param id アイテムのMinecraftID
     * @param customModelData テクスチャの番号
     * @param skullTexture プレイヤーヘッドの場合に使うテクスチャ情報
     * @param comment 追加する説明文
     * @param enchantments 追加するエンチャント
     */
    @Serializable
    data class Icon(
        val id: String = "",
        val customModelData: Int? = null,
        val skullTexture: String? = null,
        val comment: List<String>? = null,
        val enchantments: List<String>? = null
    )
}
