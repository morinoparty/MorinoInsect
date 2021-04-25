package com.github.morinoparty.morinoinsect.catching.insect

import org.bukkit.block.BlockFace

enum class SpawnDirection(val face: BlockFace) {
    NORTH(BlockFace.NORTH),
    EAST(BlockFace.EAST),
    SOUTH(BlockFace.SOUTH),
    WEST(BlockFace.WEST),
    UP(BlockFace.UP),
    DOWN(BlockFace.DOWN)
}
