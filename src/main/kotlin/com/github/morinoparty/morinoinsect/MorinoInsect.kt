package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchBroadcaster
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemFrameConverter
import com.github.morinoparty.morinoinsect.catching.converter.InsectItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.NetItemStackConverter
import com.github.morinoparty.morinoinsect.catching.converter.SpawnBlockConverter
import com.github.morinoparty.morinoinsect.catching.insect.MutableInsectTypeTable
import com.github.morinoparty.morinoinsect.catching.insect.SpawnDirection
import com.github.morinoparty.morinoinsect.catching.listener.InsectDamageListener
import com.github.morinoparty.morinoinsect.catching.listener.InsectSpawnListener
import com.github.morinoparty.morinoinsect.catching.listener.NetRightClickListener
import com.github.morinoparty.morinoinsect.catching.listener.PlaceInsectListener
import com.github.morinoparty.morinoinsect.catching.listener.SearchingWatcher
import com.github.morinoparty.morinoinsect.command.MainCommand
import com.github.morinoparty.morinoinsect.configuration.Config
import com.github.morinoparty.morinoinsect.hooker.PlaceholderApiHooker
import com.github.morinoparty.morinoinsect.hooker.VaultHooker
import org.bukkit.Bukkit
import org.bukkit.Material

class MorinoInsect : KotlinPlugin() {
    val vault = VaultHooker()
    val placeholderApiHooker = PlaceholderApiHooker()
    val scheduler = Bukkit.getScheduler()

    val insectTypeTable = MutableInsectTypeTable()
    val spawnBlockTable = mutableSetOf<Material>()

    val netConverter = NetItemStackConverter(this)
    val insectConverter = InsectItemStackConverter(this, insectTypeTable)
    val frameConverter = InsectItemFrameConverter(this)
    val spawnBlockConverter = SpawnBlockConverter(spawnBlockTable)
    val globalCatchHandlers: List<CatchHandler> = listOf(
        CatchBroadcaster()
        // TODO: 他のも実装
    )

    override fun onPluginEnable() {

        vault.hookIfEnabled(this)
        placeholderApiHooker.hookIfEnabled(this)

        applyConfig()

        server.pluginManager.apply {
            val netClickListener = NetRightClickListener(this@MorinoInsect, insectTypeTable, insectConverter, frameConverter, netConverter, spawnBlockConverter)
            val spawnListener = InsectSpawnListener(this@MorinoInsect, scheduler, insectConverter, frameConverter)
            val searchingWatcher = SearchingWatcher(insectConverter, frameConverter)
            val insectDamageListener = InsectDamageListener(insectConverter, frameConverter, netConverter, globalCatchHandlers)
            val placeInsectListener = PlaceInsectListener(insectConverter, frameConverter)
            registerEvents(netClickListener, this@MorinoInsect)
            registerEvents(spawnListener, this@MorinoInsect)
            registerEvents(searchingWatcher, this@MorinoInsect)
            registerEvents(insectDamageListener, this@MorinoInsect)
            registerEvents(placeInsectListener, this@MorinoInsect)
        }

        val manager = PaperCommandManager(this)
        val completions = manager.commandCompletions
        val mainCommand = MainCommand(this, insectTypeTable, insectConverter, netConverter)
        manager.registerCommand(mainCommand)
        completions.registerAsyncCompletion("insects") {
            insectTypeTable.types.map { it.name }
        }
        completions.registerAsyncCompletion("blocks") {
            Material.values().filter { it.isSolid }.map { block -> block.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("spawnTypes") {
            SpawnDirection.values().map { it.toString().toLowerCase() }
        }
        completions.registerAsyncCompletion("playerLocX") {
            mutableListOf(it.player.location.x.toInt().toString())
        }
        completions.registerAsyncCompletion("playerLocY") {
            mutableListOf(it.player.location.y.toInt().toString())
        }
        completions.registerAsyncCompletion("playerLocZ") {
            mutableListOf(it.player.location.z.toInt().toString())
        }
    }

    override fun onPluginDisable() {
        frameConverter.killAll()
    }

    fun applyConfig() {
        Config.load(this)

        insectTypeTable.clear()
        spawnBlockTable.clear()
        insectTypeTable.putAll(Config.insectTypeMapLoader.loadFrom(Config.insectConfig))
        spawnBlockTable.addAll(Config.insectSpawnerSetLoader.loadFrom(Config.insectConfig.insectList))
        logger.info("${insectTypeTable.rarities.size} 種類のレアリティと ${insectTypeTable.types.size} 種類の虫を読み込みました")
    }
}
