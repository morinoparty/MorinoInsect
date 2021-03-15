package com.github.morinoparty.morinoinsect.insectslist

import com.github.morinoparty.morinoinsect.condition.SpawnTypeCondition
import com.github.morinoparty.morinoinsect.condition.SpawnableBlockCondition
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// insectクラスをInsectsManagerから取得する時にnullを出したくないので代わりにNullInsectというinsectクラスを作った
/**
 * InsectBase関係の処理でnullを返したいときに使うクラス
 *
 * @sample nullInsect
 */
class NullInsect : InsectBase {

    override val reality = InsectTier.NORMAL
    override val displayName = "NullInsect"
    override val insectId = 0
    override val lengthMin = 1
    override val lengthMax = 1
    override val spawnableBlock = SpawnableBlockCondition(Material.BARRIER)
    override val spawnType = SpawnTypeCondition(SpawnType.ONGROUND)
    override val icon = ItemStack(Material.BARRIER)

    // クラスの使い方のサンプル用に定義した
    private val nullInsect = NullInsect()
}
