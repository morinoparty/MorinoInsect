package com.github.morinoparty.morinoinsect.configuration.loader

import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchCommandExecutor
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchFireworkSpawner
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import com.github.morinoparty.morinoinsect.catching.insect.InsectRarity
import com.github.morinoparty.morinoinsect.configuration.data.InsectRarityConfig

class InsectRaritySetLoader(
    private val colorLoader: TextColorLoader,
    private val playerAnnouncementLoader: PlayerAnnouncementLoader
) {
    fun loadFrom(rarityList: Map<String, InsectRarityConfig>): Set<InsectRarity> {
        return rarityList.map { rarity ->
            val catchHandlers = mutableListOf<CatchHandler>()
            rarity.value.commands?.let {
                catchHandlers.add(CatchCommandExecutor(it))
            }
            rarity.value.firework?.let {
                catchHandlers.add(CatchFireworkSpawner(it))
            }
            InsectRarity(
                name = rarity.key,
                displayName = rarity.value.displayName,
                color = colorLoader.loadFrom(rarity.value.color),
                chance = rarity.value.chance,
                catchHandlers = catchHandlers,
                catchAnnouncement = playerAnnouncementLoader.loadFrom(rarity.value.catchAnnounce)
            )
        }.toSet()
    }
}
