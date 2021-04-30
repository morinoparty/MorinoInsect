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
        val spawnCoolDown: Long = 15,
        val notSpawnCoolDown: Long = 5,
        val failureTime: Long = 30
    )
}
