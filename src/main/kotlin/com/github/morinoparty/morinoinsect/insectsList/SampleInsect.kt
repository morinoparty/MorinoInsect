package com.github.morinoparty.morinoinsect.insectsList

import com.github.morinoparty.morinoinsect.util.IInsectSpawnable
import com.github.morinoparty.morinoinsect.util.InsectTier

class SampleInsect : IInsectSpawnable {

    override var Name: String = "SampleInsect"
    override var Id: Int = 0
    override var Tier: InsectTier = InsectTier.NORMAL
}
