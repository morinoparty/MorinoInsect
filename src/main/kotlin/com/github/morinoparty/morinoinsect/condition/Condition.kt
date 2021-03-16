package com.github.morinoparty.morinoinsect.condition

/** 虫のスポーン条件を定義するときにベースとなるインターフェースクラス */
interface Condition {

    /**
     * 指定した条件にあっているか確認するメソッド
     *
     * @param targetValue 条件を確認するにあたって必要な引数
     * @return 条件にあっていればtrue,あっていなければfalse
     */
    fun checkCondition(targetValue: Any): Boolean
}
