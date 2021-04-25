package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import com.github.morinoparty.morinoinsect.catching.condition.InsectCondition
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.floor
import kotlin.random.Random

data class InsectType(
    val name: String,
    val rarity: InsectRarity,
    val lengthMin: Double,
    val lengthMax: Double,
    val icon: ItemStack,
    val conditions: Set<InsectCondition> = emptySet(),
    val catchHandlers: List<CatchHandler>
) {
    /**
     * 虫のサイズを決めるメソッド
     * @return 虫の種類とサイズを含めたInsectを返します
     */
    fun generateInsect(catcher: Player): Insect {
        check(lengthMin <= lengthMax) { "最小サイズと最大サイズが逆になっている可能性があります" }
        var rawlength = 0.0
        for (i in 0..1) {
            rawlength += (lengthMin + Random.nextDouble() * (lengthMax - lengthMin)) / 2
        }
        return Insect(this, floorToTwoDecimalPlaces(rawlength), catcher)
    }

    private fun floorToTwoDecimalPlaces(value: Double): Double = floor(value * 10) / 10
}
