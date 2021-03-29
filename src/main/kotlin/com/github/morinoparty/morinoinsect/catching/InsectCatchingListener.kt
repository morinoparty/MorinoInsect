package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.okkero.skedule.schedule
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.ArrayList

/**
 * 虫のスポーン処理をするイベントクラス
 *
 * @property plugin
 */
class InsectCatchingListener(private val plugin: MorinoInsect) : Listener {

    /** クールタイムをかけられたプレイヤーを入れておくリスト */
    private val playerInCoolTime: MutableList<Player> = ArrayList()

    /**
     * 虫取りイベントを発生させる
     * 虫取り網のクリックイベントをListenする
     *
     * @param event プレイヤーがアイテムに触れた時に作動するイベント
     */
    @EventHandler
    fun onCatchInsect(event: PlayerInteractEvent) {
        val player = event.player
        val itemInPlayerHand = player.inventory.itemInMainHand
        val playerHasNet = itemInPlayerHand.equals(InsectCatchingNet.createNetInstance())
        val playerHasCoolTime = playerInCoolTime.contains(player)
        // クールタイムは３０秒
        val coolTime: Long = 20 * 30
        if (!playerHasNet || playerHasCoolTime) return
        setCoolTime(player, coolTime)
    }

    /**
     * プレイヤーにクールタイムを設ける
     *
     * @param player 対象プレイヤー
     * @param coolTime クールタイム
     */
    private fun setCoolTime(player: Player, coolTime: Long) {
        playerInCoolTime.add(player)
        // 遅延処理　(coolTime秒待つ)
        val scheduler = Bukkit.getScheduler()
        scheduler.schedule(plugin) {
            waitFor(coolTime)
            playerInCoolTime.remove(player)
        }
    }
}
