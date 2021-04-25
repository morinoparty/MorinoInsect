package com.github.morinoparty.morinoinsect.configuration

import kotlinx.serialization.Serializable

/**
 * メッセージを格納するデータクラス
 *
 * @author うにたろう
 */
@Serializable
data class MessageConfig(
    val spawnInsect: String = "",
    val notSpawnInsect: String = "",
    val catchInsect: String = "",
    val failureInsect: String = "",

    val limitOver: String = "",
    val catchCoolDown: String = ""
)
