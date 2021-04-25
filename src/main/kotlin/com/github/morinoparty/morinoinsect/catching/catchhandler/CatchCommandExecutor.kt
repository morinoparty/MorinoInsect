package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.catching.insect.Insect

class CatchCommandExecutor(
    private val commands: List<String>
) : CatchHandler {
    override fun handle(insect: Insect) {
        val server = insect.catcher.server
        for (command in commands) {
            server.dispatchCommand(server.consoleSender, command.replace("@p", insect.catcher.name))
        }
    }
}
