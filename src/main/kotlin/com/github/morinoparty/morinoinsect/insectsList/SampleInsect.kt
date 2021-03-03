package com.github.morinoparty.morinoinsect.insectsList

import com.github.morinoparty.morinoinsect.util.IInsectSpawnable
import com.github.morinoparty.morinoinsect.util.InsectTier
import org.bukkit.Location

class SampleInsect : IInsectSpawnable {

    override var Name: String = "SampleInsect"
    override var Id: Int = 0
    override var Tier: InsectTier = InsectTier.NORMAL

    override fun spawn(spawnLocation: Location) {
        // Todo: スポーンメソッドの処理を書く（今は適当なやつにしてる）
        println(Name + " has just spawned to " + spawnLocation.x + ", " + spawnLocation.y + ", " + spawnLocation.z + " ID: " + Id + " Tier: " + Tier)
    }
}
