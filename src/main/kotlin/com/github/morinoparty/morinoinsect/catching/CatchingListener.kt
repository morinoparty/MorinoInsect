package com.github.morinoparty.morinoinsect.catching

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.lang.InterruptedException
import java.util.ArrayList

class CatchInsectsEvent : Listener {

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
        val coolTime          = 1000 * 10
        if (!playerHasNet || playerHasCoolTime) return
        setCoolTime(player, coolTime)
    }

    private fun setCoolTime(player : Player, coolTime : Int) {
        playerInCoolTime.add(player)
        try
        {
            // クールタイムの秒数はミリ秒で計算されている -> 1000 = 1秒
            Thread.sleep(coolTime.toLong())
        }
        catch (exception: InterruptedException)
        {
            exception.printStackTrace()
        }
    }
}