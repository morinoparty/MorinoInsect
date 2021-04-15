package com.github.morinoparty.morinoinsect.announcement

import org.bukkit.entity.Player

/**
 * プレイヤーやサーバー全体にアナウンスを行うためのインターフェース
 */
interface BaseAnnouncement {

    /**
     * 虫をキャッチしたプレイヤーを引数にプレイヤーのコレクションを返します
     */
    fun receiversOf(catcher: Player): Collection<Player>

    companion object {
        /**
         * 空のプレイヤーコレクションを返すメソッド
         */
        fun ofEmpty(): BaseAnnouncement =
            NoAnnouncement()

        /**
         * 受け取った引数を半径にして範囲内のプレイヤーのコレクションを返すメソッド
         */
        fun ofRanged(radius: Double): BaseAnnouncement {
            require(radius >= 0) { "半径はマイナスの値であってはなりません" }
            return RangedAnnouncement(radius)
        }

        /**
         * 虫をキャッチしたプレイヤーのみを返すメソッド
         */
        fun ofCatcherOnly(): BaseAnnouncement =
            CatcherOnlyAnnouncement()

        /**
         * サーバー内全体のプレイヤーのコレクションを返すメソッド
         */
        fun ofServerBroadcast(): BaseAnnouncement =
            ServerAnnouncement()
    }
}
