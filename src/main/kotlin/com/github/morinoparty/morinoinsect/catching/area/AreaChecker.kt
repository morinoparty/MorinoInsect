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

    // 座標がスポーン範囲の右上の角かどうか確かめるメソッド
    private fun isRightUpCorner(x: Int, y: Int): Boolean {
        val rightUpCorner = arrayOf(
            AreaCoordinates(2, 3),
            AreaCoordinates(3, 3),
            AreaCoordinates(3, 2)
        )
        return AreaCoordinates(x, y) in rightUpCorner
    }

    // 座標がスポーン範囲の右下の角かどうか確かめるメソッド
    private fun isRightDownCorner(x: Int, y: Int): Boolean {
        val rightDownCorner = arrayOf(
            AreaCoordinates(2, -3),
            AreaCoordinates(3, -2),
            AreaCoordinates(3, -3)
        )
        return AreaCoordinates(x, y) in rightDownCorner
    }

    // 座標がスポーン範囲の左上の角かどうか確かめるメソッド
    private fun isLeftUpCorner(x: Int, y: Int): Boolean {
        val leftUpCorner = arrayOf(
            AreaCoordinates(-2, 3),
            AreaCoordinates(-3, 2),
            AreaCoordinates(-3, 3)
        )
        return AreaCoordinates(x, y) in leftUpCorner
    }

    // 座標がスポーン範囲の左下の角かどうか確かめるメソッド
    private fun isLeftDownCorner(x: Int, y: Int): Boolean {
        val leftDownCorner = arrayOf(
            AreaCoordinates(-2, -3),
            AreaCoordinates(-3, -2),
            AreaCoordinates(-3, -3)
        )
        return AreaCoordinates(x, y) in leftDownCorner
    }
}
