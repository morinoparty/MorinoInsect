package com.github.morinoparty.morinoinsect.insectslist

import com.github.morinoparty.morinoinsect.extentions.selectRandomly
import kotlin.collections.ArrayList

/** 虫のデータを管理するクラス */
class InsectsManager {

    /** 虫のデータを保管しておくリスト */
    private val insects: MutableList<InsectBase> = ArrayList()

    /**
     * 新たに虫のデータを登録する
     *
     * @param insect 新たに登録する虫
     */
    fun registerInsect(insect: InsectBase) {
        if (insectExists(insect)) return
        insects.add(insect)
    }

    /**
     * 虫がデータに保管されているか確かめる
     *
     * @param insect 対象の虫
     * @return 保管されていなければfalse,保管されていればtrue
     */
    fun insectExists(insect: InsectBase): Boolean {
        return insects.contains(insect)
    }

    /**
     * 引数の虫IDと保管されている虫IDと一致する虫データを取り出す
     *
     * @param insectId 虫ID
     * @return 引数の虫IDと保管されている虫IDと一致する虫データ
     */
    fun obtainInsectData(insectId: Int): InsectBase {
        // InsectBaseの定義にnullが使えないので代わりにNullInsectという代わりのクラスを定義している
        var insect: InsectBase = NullInsect()
        for (insectData in insects) {
            val isInsectDataMatched = insectData.insectId == (insectId)
            if (!isInsectDataMatched) continue
            insect = insectData
        }
        return insect
    }

    /**
     * 引数の虫の名前と保管されている虫の名前と一致する虫データを取り出す
     *
     * @param insectName 虫の名前
     * @return 引数の虫の名前と保管されている虫の名前と一致する虫データ
     */
    fun obtainInsectData(insectName: String): InsectBase {
        var insect: InsectBase = NullInsect()
        for (insectData in insects) {
            val isInsectDataMatched = insectData.displayName == insectName
            if (!isInsectDataMatched) continue
            insect = insectData
        }
        return insect
    }

    // １回のスポーンで２匹の虫をスポーンさせたいため、ランダムに抽出する虫の数を２匹とする
    /**
     * 虫データが保管されているinsectsリストからランダムに二つの虫データを選んでリストに入れて返す
     *
     * @return 虫が保管されているリストからランダムに選んだ２つの虫データが入ったリスト
     */
    fun selectInsectRandomly(): MutableList<InsectBase> {
        return insects.selectRandomly()
    }
}
