package com.github.morinoparty.morinoinsect.configuration

import kotlinx.serialization.Serializable

/**
 * メッセージを格納するデータクラス
 *
 * @author うにたろう
 */
@Serializable
data class Message(
    val testMessage: String = "",
    val sampleMessage: String = ""
)
