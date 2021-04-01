package com.github.morinoparty.morinoinsect.configuration

import kotlinx.serialization.Serializable

/**
 * 基本的なコンフィグを格納するデータクラス
 */
@Serializable
data class StandardConfig(
    val general: General = General()
) {
    @Serializable
    data class General(
        val test: String = ""
    )
}