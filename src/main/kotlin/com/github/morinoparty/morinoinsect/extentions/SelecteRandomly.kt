package com.github.morinoparty.morinoinsect.extentions

import kotlin.random.Random

// repeat(2)と、２回繰り返すことが確定しているのは、無造作に取り出す要素はプラグインの仕様上２個までと決まっているからだ
/**
 * MutableListの中からランダムに２つの要素を取り出す拡張関数
 *
 * @receiver 型指定なしのMutableList
 * @return ランダムに取り出した要素が入ったMutableList
 */
fun <T> MutableList<T>.selectRandomly(): MutableList<T> {
    val selected = mutableListOf<T>()
    val remaining = toMutableList()
    // ２個の要素を無造作に選ぶ処理
    repeat(2) {
        val remainingCount = remaining.size
        val randomizer = Random
        val randomIndex = randomizer.nextInt(remainingCount)
        selected += remaining[randomIndex]
        val lastIndex = remainingCount - 1
        val lastValue = remaining.removeAt(lastIndex)
        // ランダムに選択された要素が末尾以外なら、それを末尾の要素で置き換える
        if (randomIndex < lastIndex) {
            remaining[randomIndex] = lastValue
        }
    }
    return selected
}

/**
 * MutableListの中からランダムに１つの要素を取り出す拡張関数
 *
 * @receiver 型指定なしのMutableList
 * @return ランダムに取り出した要素が入ったMutableList
 */
fun <T> MutableList<T>.selectOneRandomly(): T {
    val list = toMutableList()
    // １個の要素を無造作に選ぶ処理
    val valuesCount = list.count()
    val randomizer = Random
    val randomIndex = randomizer.nextInt(valuesCount)
    return list[randomIndex]
}
