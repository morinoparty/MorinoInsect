package com.github.morinoparty.morinoinsect.configuration

import kotlinx.serialization.Serializable

/**
 * 基本的なコンフィグを格納するデータクラス
 */
@Serializable
data class StandardConfig(
    val general: General = General(),
    val messages: Messages = Messages(),
    val contestPrizes: Map<String, List<String>> = mapOf(),
    val autoRunning: AutoRunning = AutoRunning()
) {
    @Serializable
    data class General(
        val autoStart: Boolean = false,
        val useBossBar: Boolean = true,
        val onlyForContest: Boolean = false,
        val noCatchingUnlessContest: Boolean = false,
        val contestDisabledWorlds: List<String> = listOf(),
        val saveRecords: Boolean = false,
        val spawnCoolDown: Long = 15,
        val notSpawnCoolDown: Long = 5,
        val failureTime: Long = 30
    )

    @Serializable
    data class AutoRunning(
        val enable: Boolean = false,
        val requiredPlayers: Int = 5,
        val timer: Long = 300,
        val startTime: List<String> = listOf()
    )

    @Serializable
    data class Messages(
        val announceNew1st: Int = -1,
        val onlyAnnounceCatchingNet: Boolean = false,
        val broadcastStart: Boolean = true,
        val broadcastStop: Boolean = true,
        val showTopOnEnding: Boolean = true,
        val contestBarColor: String = "blue",
        val topNumber: Int = 3
    )
}
