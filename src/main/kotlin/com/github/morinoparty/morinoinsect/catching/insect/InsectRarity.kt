package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.announcement.BaseAnnouncement
import com.github.morinoparty.morinoinsect.catching.catchhandler.CatchHandler
import net.kyori.adventure.text.format.TextColor

data class InsectRarity(
    val name: String,
    val displayName: String,
    val color: TextColor,
    val chance: Double,
    val catchHandlers: List<CatchHandler>,
    val catchAnnouncement: BaseAnnouncement
)
