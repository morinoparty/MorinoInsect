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
    val failureInsect: String = "",
    val catchInsect: String = "",
    val get1st: String = "",

    val limitOver: String = "",
    val catchCoolDown: String = "",
    val spawnNowInsect: String = "",

    val contestStart: String = "<#66bb6a>[むしとり] <r>虫取り大会スタートです！",
    val contestStartTimer: String = "<#66bb6a>[むしとり] <r>虫取り大会の制限時間は <time> です！",
    val contestStop: String = "<#66bb6a>[むしとり] <r>虫取り大会終了です！",
    val timerBossBar: String = "<#66bb6a>[むしとり] <aqua><b>虫取り大会 <r>[残り時間 <time>]",
    val timeFormatMinutes: String = "分",
    val timeFormatSeconds: String = "秒",

    val topList: String = "<#66bb6a>[むしとり]<r> <yellow><ordinal>. <gray>: <player>, <length>mm <insect>",
    val topNoRecord: String = "<#66bb6a>[むしとり]<r> 誰もまだ記録を持っていません",
    val topMine: String = "<#66bb6a>[むしとり]<r> あなたは <ordinal>: <length>cm <insect>",
    val topMineNoRecord: String = "<#66bb6a>[むしとり]<r> あなたは賞を獲得していません"
)
