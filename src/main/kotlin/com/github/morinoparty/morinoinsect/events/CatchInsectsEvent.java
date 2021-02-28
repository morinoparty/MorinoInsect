package com.github.morinoparty.morinoinsect.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class CatchInsectsEvent implements Listener {

    private List<Player> playerInCoolTime = new ArrayList<>();

    @EventHandler
    public void onCatch(PlayerInteractEvent event)
    {
        Player player             = event.getPlayer();
        Material itemInPlayerHand = player.getInventory().getItemInMainHand().getType();
        boolean playerHasNet      = itemInPlayerHand.equals(Material.FISHING_ROD);
        boolean playerHasCoolTime = playerInCoolTime.contains(player);
        // クールタイムは１０秒
        int coolTime              = 1000 * 10;
        if (!playerHasNet || playerHasCoolTime) return;
        setCoolTime(player, coolTime);
    }

    private void setCoolTime(Player player, int coolTime)
    {
        playerInCoolTime.add(player);
        try
        {
            // クールタイムの秒数はミリ秒で計算されている -> 1000 = 1秒
            Thread.sleep(coolTime);
        }
        catch(InterruptedException exception)
        {
            exception.printStackTrace();
        }
    }
}
