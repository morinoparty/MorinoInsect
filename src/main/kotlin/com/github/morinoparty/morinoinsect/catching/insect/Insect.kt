package com.github.morinoparty.morinoinsect.catching.insect

import org.bukkit.entity.Player

/**
 * 虫と虫のサイズを保存するクラス
 */
data class Insect(
    val type: InsectType,
    val length: Double,
    val catcher: Player
)
