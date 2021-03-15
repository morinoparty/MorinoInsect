package com.github.morinoparty.morinoinsect

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin
import co.aikar.commands.PaperCommandManager
import com.github.morinoparty.morinoinsect.catching.CatchingListener
import com.github.morinoparty.morinoinsect.catching.SpawningInsectsListener
import com.github.morinoparty.morinoinsect.command.MainCommand

/** プロジェクトのメインクラス */
class MorinoInsect : KotlinPlugin() {

    /**
     * プラグイン起動時の処理を書くメソッド
     * ここでは他クラスに渡す用のインスタンスの初期化とコマンド、イベントの登録を行っている
     */
    override fun onPluginEnable() {
        // Plugin startup logic
        instance = this
        registerCommands()
        registerEvents()
    }

    /**
     * プラグインシャットダウン時の処理を書くメソッド
     * 特に書くことは無い
     */
    override fun onPluginDisable() {
        // Plugin shutdown logic
    }

    companion object {

        /** 他クラスに渡す用のインスタンス */
        private lateinit var instance: MorinoInsect

        /**
         * 他クラスにこのメインクラスのインスタンスを渡すメソッド
         *
         * @return このメインクラスのインスタンス
         */
        fun getInstance(): MorinoInsect {
            return instance
        }
    }

    /** イベントを登録するメソッド */
    private fun registerEvents() {
        val listenerManager = this.server.pluginManager
        listenerManager.registerEvents(CatchingListener(this), this)
        listenerManager.registerEvents(SpawningInsectsListener(this), this)
    }

    /** コマンドを登録するメソッド */
    private fun registerCommands() {
        val commandManager = PaperCommandManager(this)
        val mainCommand = MainCommand(this)
        commandManager.registerCommand(mainCommand)
    }
}
