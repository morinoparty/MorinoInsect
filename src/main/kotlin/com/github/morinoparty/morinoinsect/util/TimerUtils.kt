package com.github.morinoparty.morinoinsect.util

import com.github.morinoparty.morinoinsect.configuration.Config
import java.time.Duration

object TimerUtils {
    fun time(second: Long): String {
        val builder = StringBuilder()
        val duration = Duration.ofSeconds(second)

        if (duration.toMinutes() > 0) {
            builder.append(duration.toMinutes())
                .append(Config.messageConfig.timeFormatMinutes)
                .append(" ")
        }
        builder.append(duration.seconds % 60)
            .append(Config.messageConfig.timeFormatSeconds)
        return builder.toString()
    }
}
