package com.github.morinoparty.morinoinsect.dao.yaml

import com.github.morinoparty.morinoinsect.catching.competition.Record
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectTypeTable
import com.github.morinoparty.morinoinsect.dao.RecordDao
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.UUID
import kotlin.math.min

class YamlRecordDao(
    private val plugin: Plugin,
    private val insectTypeTable: InsectTypeTable
) : RecordDao {
    private val yaml: YamlConfiguration
    private val file: File

    init {
        val path = plugin.dataFolder.toPath().resolve("records.yml")
        file = path.toFile()
        if (!file.exists()) {
            file.createNewFile()
        }
        yaml = YamlConfiguration.loadConfiguration(file)
    }

    override fun insert(record: Record) {
        val id = record.insect.catcher.uniqueId.toString()
        require(!yaml.contains(id)) { "Record must not exist in the ranking" }

        setRecord(yaml.createSection(id), record)
        yaml.save(file)
    }

    override fun update(record: Record) {
        val id = record.insect.catcher.uniqueId.toString()
        require(yaml.contains(id)) { "Record must exist in the ranking" }

        setRecord(
            yaml.getConfigurationSection(id)
                ?: throw IllegalStateException("Couldn't get configuration section"),
            record
        )
        yaml.save(file)
    }

    private fun setRecord(section: ConfigurationSection, record: Record) {
        section.set("insectType", record.insect.type.name)
        section.set("insectLength", record.insect.length)
    }

    override fun clear() {
        for (key in yaml.getKeys(false)) {
            yaml.set(key, null)
        }
        yaml.save(file)
    }

    override fun top(size: Int): List<Record> {
        val list = all()
        return list.subList(0, min(5, list.size))
    }

    override fun all(): List<Record> {
        val records = mutableListOf<Record>()
        for (section in yaml.getKeys(false).map(yaml::getConfigurationSection)) {
            val id = UUID.fromString(section?.name)
            val player = plugin.server.getPlayer(id)

            val insectTypeName = section?.getString("insectType")
            val insectType = insectTypeTable.types.find { it.name == insectTypeName }
                ?: throw IllegalStateException("Insect type doesn't exist for '$insectTypeName'")
            val insectLength = section?.getDouble("insectLength")

            val insect = Insect(
                insectType,
                insectLength
                    ?: throw IllegalStateException("Illegal insect type"),
                player ?: throw IllegalStateException("Illegal player")
            )
            records.add(Record(insect))
        }
        return records.sortedDescending()
    }
}
