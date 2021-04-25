package com.github.morinoparty.morinoinsect.catching.catchhandler

import com.github.morinoparty.morinoinsect.catching.insect.Insect

/**
 * 虫を捕まえた時に行う処理をまとめるインターフェース
 */
interface CatchHandler {
    /**
     * 行う処理
     */
    fun handle(insect: Insect)
}
