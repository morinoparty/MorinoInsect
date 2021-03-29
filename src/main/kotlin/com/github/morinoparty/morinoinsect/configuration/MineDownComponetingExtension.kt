package com.github.morinoparty.morinoinsect.configuration

import de.themoep.minedown.adventure.MineDown
import net.kyori.adventure.text.Component

/**
 * MineDown構文で書かれたString型の文字列をComponentにして返すメソッド
 */
internal fun String.toComponent(): Component =
    MineDown.parse(this)

/**
 * MineDown構文で書かれたString型のListをComponentにして返すメソッド
 */
internal fun List<String>.toComponent(): List<Component> =
    this.map(String::toComponent)
