package com.github.morinoparty.morinoinsect.insectslist

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// insectクラスをInsectsManagerから取得する時にnullを出したくないので代わりにNullInsectというinsectクラスを作った
class NullInsect : IInsectBase {

    override val reality = InsectTier.NORMAL
    override val displayName = "NullInsect"
    override val insectId = 0
    override val lengthMin = 1
    override val lengthMax = 1
    override val spawnableBlock = Material.BARRIER
    override val spawnType = SpawnType.ONGROUND
    override val icon = ItemStack(Material.BARRIER)
}
