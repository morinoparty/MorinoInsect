package com.github.morinoparty.morinoinsect.configuration.loader

import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchCommandExecutor
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import com.github.morinoparty.morinoinsect.catching.insect.InsectRarity
import com.github.morinoparty.morinoinsect.catching.insect.InsectType
import com.github.morinoparty.morinoinsect.configuration.InsectConfig

class InsectTypeMapLoader(
    private val insectRaritySetLoader: InsectRaritySetLoader,
    private val customItemStackLoader: CustomItemStackLoader,
    private val insectConditionSetLoader: InsectConditionSetLoader
) {
    fun loadFrom(insectConfig: InsectConfig): Map<InsectRarity, Set<InsectType>> {
        val rarities = insectRaritySetLoader.loadFrom(insectConfig.rarityList)
        return rarities.associateWith { rarity ->
            val insectTypes = insectConfig.insectList
            insectTypes.filter {
                rarity.name == it.value.rarity
            }.map { type ->
                val catchHandlers = mutableListOf<CatchHandler>()
                catchHandlers.addAll(rarity.catchHandlers)
                type.value.commands?.let {
                    catchHandlers.add(CatchCommandExecutor(it))
                }

                InsectType(
                    name = type.key,
                    rarity = rarity,
                    lengthMax = type.value.lengthMax,
                    lengthMin = type.value.lengthMin,
                    catchHandlers = catchHandlers,
                    icon = customItemStackLoader.loadFrom(type.value.icon),
                    conditions = insectConditionSetLoader.loadFrom(type.value.conditions)
                )
            }.toSet()
        }
    }
}
