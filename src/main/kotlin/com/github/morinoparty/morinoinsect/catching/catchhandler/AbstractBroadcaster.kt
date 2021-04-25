package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.announcement.BaseAnnouncement
import com.github.morinoparty.morinoinsect.catching.insect.Insect
import com.github.morinoparty.morinoinsect.catching.insect.InsectType
import com.github.morinoparty.morinoinsect.configuration.format.TextFormat
import com.github.morinoparty.morinoinsect.util.miniMessage
import com.github.morinoparty.morinoinsect.util.plainText
import net.kyori.adventure.text.minimessage.Template

abstract class AbstractBroadcaster : CatchHandler {
    abstract val catchMessageFormat: TextFormat

    abstract fun meetBroadcastCondition(insect: Insect): Boolean

    abstract fun announcement(insect: Insect): BaseAnnouncement

    override fun handle(insect: Insect) {
        if (meetBroadcastCondition(insect)) {
            val receivers = insect.type.rarity.catchAnnouncement.receiversOf(insect.catcher).toMutableList()
            val msg = catchMessageFormat.replace(insect.catcher).miniMessage(
                listOf(
                    Template.of("player", insect.catcher.name),
                    Template.of("length", insect.length.toString()),
                    Template.of("rarity", insect.catcher.name),
                    Template.of("rarity_color", "<${insect.type.rarity.color.asHexString()}>"),
                    Template.of("insect", insect.type.icon.itemMeta.displayName()?.plainText() ?: insect.type.name),
                    Template.of("insect_with_rarity", insectNameWithRarity(insect.type))
                )
            )
            for (receiver in receivers) {
                receiver.sendMessage(msg)
            }
        }
    }

    private fun insectNameWithRarity(insectType: InsectType): String {
        return "${insectType.rarity.displayName.toUpperCase()} ${insectType.icon.itemMeta.displayName()?.plainText()}"
    }
}
