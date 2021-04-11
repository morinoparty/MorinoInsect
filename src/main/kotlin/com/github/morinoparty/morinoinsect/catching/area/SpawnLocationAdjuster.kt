package com.github.morinoparty.morinoinsect.catching.area

import org.bukkit.Location
import org.bukkit.block.BlockFace

interface SpawnLocationAdjuster {

    /**
     * 虫がスポーンする向きによってスポーンする場所も調整するメソッド
     *
     * @param direction 虫がスポーンする向き
     * @return スポーンする向きに対応したLocationを返す (Example: UP -> this.add(0.0, 1.0, 0.0))
     */
    fun Location.adjustSpawnLocation(direction: BlockFace): Location {
        return when (direction) {
            BlockFace.UP -> this.add(0.0, 1.0, 0.0)
            BlockFace.DOWN -> this.add(0.0, -1.0, 0.0)
            BlockFace.SOUTH -> this.add(0.0, 0.0, 1.0)
            BlockFace.NORTH -> this.add(0.0, 0.0, -1.0)
            BlockFace.EAST -> this.add(1.0, 0.0, 0.0)
            BlockFace.WEST -> this.add(-1.0, 0.0, 0.0)
            else -> this
        }
    }
}
