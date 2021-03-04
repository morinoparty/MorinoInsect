package com.github.morinoparty.morinoinsect.util

import org.bukkit.Location

interface IInsectSpawnable {

    var Name: String
    var Id: Int
    var Tier: InsectTier

    fun spawn(spawnLocation: Location) {
        // Todo: スポーンメソッドの処理を書く（今は適当なやつにしてる）
        println(Name + " has just spawned to " + spawnLocation.x + ", " + spawnLocation.y + ", " + spawnLocation.z + " ID: " + Id + " Tier: " + Tier)
    }
}
