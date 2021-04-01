package com.github.morinoparty.morinoinsect.catching.area

/**
 * 虫のスポーン範囲の角を確認するクラス
 */
class AreaChecker(private val x: Int, private val y: Int) {

    /**
     * 座標をもとに、それがスポーン範囲の角かどうかを確かめるメソッド
     *
     * @param x x座標
     * @param y y座標
     * @return スポーン範囲の角であればtrue、そうでなければfalseを返す
     */
    fun isSquareCorner(): Boolean {
        return when {
            isRightUpCorner(x, y) -> true
            isRightDownCorner(x, y) -> true
            isLeftUpCorner(x, y) -> true
            isLeftDownCorner(x, y) -> true
            else -> false
        }
    }

    private fun isRightUpCorner(x: Int, y: Int): Boolean {
        val rightUpCorner = arrayOf(
            AreaCoordinates(2, 3),
            AreaCoordinates(3, 3),
            AreaCoordinates(3, 2)
        )
        for (i in 0..rightUpCorner.size step 1) {
            return rightUpCorner[i].x == x && rightUpCorner[i].y == y
        }
        return false
    }

    private fun isRightDownCorner(x: Int, y: Int): Boolean {
        val rightDownCorner = arrayOf(
            AreaCoordinates(2, -3),
            AreaCoordinates(3, -2),
            AreaCoordinates(3, -3)
        )
        for (i in 0..rightDownCorner.size step 1) {
            return rightDownCorner[i].x == x && rightDownCorner[i].y == y
        }
        return false
    }

    private fun isLeftUpCorner(x: Int, y: Int): Boolean {
        val leftUpCorner = arrayOf(
            AreaCoordinates(-2, 3),
            AreaCoordinates(-3, 2),
            AreaCoordinates(-3, 3)
        )
        for (i in 0..leftUpCorner.size step 1) {
            return leftUpCorner[i].x == x && leftUpCorner[i].y == y
        }
        return false
    }

    private fun isLeftDownCorner(x: Int, y: Int): Boolean {
        val leftDownCorner = arrayOf(
            AreaCoordinates(-2, -3),
            AreaCoordinates(-3, -2),
            AreaCoordinates(-3, -3)
        )
        for (i in 0..leftDownCorner.size step 1) {
            return leftDownCorner[i].x == x && leftDownCorner[i].y == y
        }
        return false
    }
}
