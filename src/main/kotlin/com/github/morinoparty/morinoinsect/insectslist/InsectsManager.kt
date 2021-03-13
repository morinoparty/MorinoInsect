package com.github.morinoparty.morinoinsect.insectslist

import com.github.morinoparty.morinoinsect.extentions.selectRandomly
import kotlin.collections.ArrayList

class InsectsManager {

    private val insects: MutableList<InsectBase> = ArrayList()

    fun registerInsect(insect: InsectBase) {
        if (insectExists(insect)) return
        insects.add(insect)
    }

    fun insectExists(insect: InsectBase): Boolean {
        return insects.contains(insect)
    }

    fun obtainInsectData(insectId: Int): InsectBase {
        var insect: InsectBase = NullInsect()
        for (insectData in insects) {
            val isInsectDataMatched = insectData.insectId == (insectId)
            if (!isInsectDataMatched) continue
            insect = insectData
        }
        return insect
    }

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
    fun selectInsectRandomly(): MutableList<InsectBase> {
        return insects.selectRandomly()
    }
}
