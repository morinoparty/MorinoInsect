package com.github.morinoparty.morinoinsect.configuration

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import br.com.devsrsouza.kotlinbukkitapi.serialization.architecture.config
import com.github.morinoparty.morinoinsect.configuration.loader.CustomItemStackLoader
import com.github.morinoparty.morinoinsect.configuration.loader.EnchantmentMapLoader
import com.github.morinoparty.morinoinsect.configuration.loader.InsectConditionSetLoader
import com.github.morinoparty.morinoinsect.configuration.loader.InsectRaritySetLoader
import com.github.morinoparty.morinoinsect.configuration.loader.InsectSpawnerSetLoader
import com.github.morinoparty.morinoinsect.configuration.loader.InsectTypeMapLoader
import com.github.morinoparty.morinoinsect.configuration.loader.PlayerAnnouncementLoader
import com.github.morinoparty.morinoinsect.configuration.loader.TextColorLoader
import java.nio.file.Path
import java.nio.file.Paths

object Config {
    lateinit var standardConfig: StandardConfig
    lateinit var insectConfig: InsectConfig
    lateinit var messageConfig: MessageConfig

    val textColorLoader = TextColorLoader()
    val enchantmentMapLoader = EnchantmentMapLoader()
    val customItemStackLoader = CustomItemStackLoader(enchantmentMapLoader)
    val playerAnnouncementLoader = PlayerAnnouncementLoader()
    val insectRaritySetLoader = InsectRaritySetLoader(textColorLoader, playerAnnouncementLoader)
    val insectConditionSetLoader = InsectConditionSetLoader()

    val insectTypeMapLoader = InsectTypeMapLoader(insectRaritySetLoader, customItemStackLoader, insectConditionSetLoader)
    val insectSpawnerSetLoader = InsectSpawnerSetLoader()

    fun load(plugin: KotlinPlugin) {
        val configPath = Paths.get("config.yml")
        val localePath = Paths.get("locale")
        canSaveDefault(plugin, configPath)
        canSaveDefault(plugin, localePath.resolve("insect.yml"))
        canSaveDefault(plugin, localePath.resolve("message.yml"))

        standardConfig = plugin.config(configPath.toString(), StandardConfig(), StandardConfig.serializer(), alwaysRestoreDefaults = false).config
        insectConfig = plugin.config(localePath.resolve("insect.yml").toString(), InsectConfig(), InsectConfig.serializer(), alwaysRestoreDefaults = false).config
        messageConfig = plugin.config(localePath.resolve("message.yml").toString(), MessageConfig(), MessageConfig.serializer(), alwaysRestoreDefaults = false).config
    }

    private fun canSaveDefault(plugin: KotlinPlugin, resourcePath: Path) {
        val dataPath = plugin.dataFolder.toPath()
        val configurationFile = dataPath.resolve(resourcePath).toFile()

        if (!configurationFile.exists()) {
            plugin.saveResource(resourcePath.toString(), false)
        }
    }
}
