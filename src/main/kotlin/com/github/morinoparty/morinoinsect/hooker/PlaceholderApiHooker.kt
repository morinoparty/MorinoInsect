package com.github.morinoparty.morinoinsect.hooker

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.configuration.format.Format
import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlaceholderApiHooker : PluginHooker {
    override val pluginName = "PlaceholderAPI"
    override var hasHooked = false

    override fun hook(plugin: MorinoInsect) {
        MorinoInsectPlaceholder(plugin).register()
        Format.init(this)
        hasHooked = true
    }

    fun tryReplacing(string: String, player: Player) = PlaceholderAPI.setPlaceholders(player, string)

    class MorinoInsectPlaceholder(val morinoInsect: MorinoInsect) : PlaceholderExpansion() {

        // private val competition: CatchingCompetition = morinoInsect.competition

        override fun getRequiredPlugin() = "MorinoInsect"

        override fun canRegister() = morinoInsect == Bukkit.getPluginManager().getPlugin(requiredPlugin)

        override fun getIdentifier() = ""

        override fun getAuthor() = morinoInsect.description.authors[0] ?: "morinoparty"

        override fun getVersion() = morinoInsect.description.version

        /*
        override fun onPlaceholderRequest(player: Player?, identifier: String): String {
            return when {
                identifier.startsWith("top_player_") -> {
                    val number = identifier.replace("top_player_", "").toInt()
                    if (competition.ranking.size >= number)
                        competition.recordOf(number).fisher.name ?: "null"
                    else
                        "no one"
                }
                identifier.startsWith("top_insect_length_") -> {
                    val number = identifier.replace("top_insect_length_", "").toInt()
                    if (competition.ranking.size >= number)
                        competition.recordOf(number).fish.length.toString()
                    else
                        "0.0"
                }
                identifier.startsWith("top_insect_") -> {
                    val number = identifier.replace("top_insect_", "").toInt()
                    if (competition.ranking.size >= number)
                        competition.recordOf(number).fish.type.name
                    else
                        "none"
                }
                identifier == "rank" -> {
                    require(player != null) { "'rank' placeholder requires a player" }
                    if (competition.containsContestant(player)) {
                        val record = competition.recordOf(player)
                        competition.rankNumberOf(record).toString()
                    } else {
                        "0"
                    }
                }
                identifier == "insect_length" -> {
                    require(player != null) { "'insect_length' placeholder requires a player" }
                    if (competition.containsContestant(player))
                        competition.recordOf(player).fish.length.toString()
                    else
                        "0.0"
                }
                identifier == "insect" -> {
                    require(player != null) { "'insect' placeholder requires a player" }
                    if (competition.containsContestant(player))
                        competition.recordOf(player).fish.type.name
                    else
                        "none"
                }
                else -> ""
            }
        }
         */
    }
}
