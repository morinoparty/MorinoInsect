package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.catching.insect.Insect
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework

class CatchFireworkSpawner(private val amount: Int) : CatchHandler {
    override fun handle(insect: Insect) {
        val effect = FireworkEffect.builder()
            .with(FireworkEffect.Type.BALL_LARGE)
            .withColor(effectConverter(insect.type.rarity.color))
            .withFade()
            .withTrail()
            .withFlicker()
            .build()
        for (i in 1..amount) {
            insect.catcher.location.let {
                val firework = it.world.spawn(it.add(0.0, 1.0, 0.0), Firework::class.java)
                val meta = firework.fireworkMeta
                meta.addEffect(effect)
                meta.power = i
                firework.fireworkMeta = meta
            }
        }
    }

    private fun effectConverter(color: TextColor): Color {
        return when (NamedTextColor.nearestTo(color)) {
            NamedTextColor.AQUA -> Color.AQUA
            NamedTextColor.BLACK -> Color.BLACK
            NamedTextColor.BLUE -> Color.NAVY
            NamedTextColor.DARK_AQUA -> Color.TEAL
            NamedTextColor.DARK_BLUE -> Color.BLUE
            NamedTextColor.DARK_GRAY -> Color.SILVER
            NamedTextColor.DARK_GREEN -> Color.GREEN
            NamedTextColor.DARK_PURPLE -> Color.FUCHSIA
            NamedTextColor.DARK_RED -> Color.RED
            NamedTextColor.GOLD -> Color.ORANGE
            NamedTextColor.GRAY -> Color.GRAY
            NamedTextColor.GREEN -> Color.LIME
            NamedTextColor.LIGHT_PURPLE -> Color.PURPLE
            NamedTextColor.RED -> Color.MAROON
            NamedTextColor.WHITE -> Color.WHITE
            NamedTextColor.YELLOW -> Color.YELLOW
            else -> Color.WHITE
        }
    }
}
