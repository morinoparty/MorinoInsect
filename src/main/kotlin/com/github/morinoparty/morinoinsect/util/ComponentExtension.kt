package com.github.morinoparty.morinoinsect.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.Template
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer

/**
 * Componentをプレーンテキストに変換するメソッド
 */
internal fun Component.plainText(): String = PlainComponentSerializer.plain().serialize(this)

internal fun Component.serialize(): String = MiniMessage.get().serialize(this)

/**
 * テキストをAdventureMiniMessageに変換するメソッド
 * https://docs.adventure.kyori.net/minimessage.html
 */
internal fun String.miniMessage(templates: List<Template>): Component {
    return MiniMessage.get().parse(this, templates).decoration(TextDecoration.ITALIC, false)
}

internal fun String.miniMessage(): Component {
    return MiniMessage.get().parse(this).decoration(TextDecoration.ITALIC, false)
}

internal fun List<String>.miniMessage(template: List<Template>): List<Component> {
    return this.map { it.miniMessage(template) }
}

internal fun List<String>.miniMessage(): List<Component> {
    return this.map { it.miniMessage() }
}
