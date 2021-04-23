package com.github.morinoparty.morinoinsect.catching.area

import org.bukkit.Location
import org.bukkit.block.Block
import java.lang.Math.sqrt

/**
 * ブロックを検知する時に使うメソッドを持つクラス
 *
 * @param centerLoc 検知する範囲の中心の座標
 * @param radius 検知する範囲
 */
class BlockDetector(private val centerLoc: Location, private val radius: Int) {

    /**
     * 円形の範囲にあるブロックを検出して取り出すメソッド
     *
     * - [参考にしたアルゴリズムのサイト](https://yttm-work.jp/collision/collision_0003.html)
     *
     * @return 取り出したブロックをSet型として返す
     */
    fun detectBlocksOfSphere(): Set<Block> {
        val blocksOfSphere = mutableSetOf<Block>()
        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {

                    // 指定した円の範囲の中にあるブロックを取り出してSetに入れる
                    if (sqrt((x * x) + (y * y) + (z * z).toDouble()) >= radius) continue
                    val block = centerLoc.world.getBlockAt(
                        x + centerLoc.blockX,
                        y + centerLoc.blockY,
                        z + centerLoc.blockZ
                    )
                    blocksOfSphere.add(block)
                }
            }
        }
        return blocksOfSphere
    }
}
