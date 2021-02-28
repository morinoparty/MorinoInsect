package com.github.morinoparty.morinoinsect.catching

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.okkero.skedule.schedule
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.ArrayList

class CatchInsectsEvent(val plugin : MorinoInsect) : Listener {

    private val playerInCoolTime : MutableList<Player> = ArrayList()

    // 虫取りイベントを発生させる
    // 虫取り網のクリックイベントをListenする
    @EventHandler
    fun onCatchInsect(event : PlayerInteractEvent) {
        val player            = event.player
        val itemInPlayerHand  = player.inventory.itemInMainHand.type
        // TODO: 虫取り網の作成(現在はFISHING_RODになっている)
        val playerHasNet      = itemInPlayerHand == Material.FISHING_ROD
        val playerHasCoolTime = playerInCoolTime.contains(player)
        // TODO: クールタイムの秒数を決める
        val coolTime : Long   = 200
        if (!playerHasNet || playerHasCoolTime) return
        setCoolTime(player, coolTime)
    }

    private fun setCoolTime(player : Player, coolTime : Long) {
        playerInCoolTime.add(player)
        // 遅延処理　(coolTime秒待つ)
        val scheduler = Bukkit.getScheduler()
        scheduler.schedule(plugin)
        {
            waitFor(coolTime)
            playerInCoolTime.remove(player)
        }
    }
}