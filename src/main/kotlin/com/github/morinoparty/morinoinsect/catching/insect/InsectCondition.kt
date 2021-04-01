package com.github.morinoparty.morinoinsect.catching.insect

import com.github.morinoparty.morinoinsect.catching.condition.BlockCondition
import com.github.morinoparty.morinoinsect.catching.condition.Condition
import com.github.morinoparty.morinoinsect.catching.condition.TimeCondition
import kotlinx.serialization.Serializable
import org.bukkit.Material

/**
 * 虫のコンディションデータクラス
 *
 * @constructor 虫のコンディション一覧
 * @param blocks スポーン可能なブロック (例：stone,dark_oak_log)
 * @param direction スポーンする位置 (例：tree|ground)
 * @param time スポーンする時間　(day or night)
 * @author うにたろう
 */
@Serializable
data class InsectCondition(
    val blocks: List<String> = emptyList(),
    val spawnType: String = "",
    val time: String? = null
) {
    /**
     * @return 発生条件を全てまとめてセットで返します
     */
    fun generateConditionSet(): Set<Condition> {
        return setOfNotNull(
            BlockCondition(blocks.map { Material.valueOf(it.toUpperCase()) }),
            TimeCondition(time?.replace("day", "true")?.replace("night", "false").toBoolean())
        )
    }
}
