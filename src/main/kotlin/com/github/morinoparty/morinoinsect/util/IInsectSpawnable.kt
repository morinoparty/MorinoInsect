package com.github.morinoparty.morinoinsect.util

import org.bukkit.Location

interface IInsectSpawnable {

    var Name: String
    var Id: Int
    var Tier: InsectTier

    fun spawn(spawnLocation: Location)
}
