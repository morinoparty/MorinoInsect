package com.github.morinoparty.morinoinsect.insectslist

class InsectTierManager {

    companion object {

        private val tiers: MutableList<InsectTier> = ArrayList()

        fun append(tier: InsectTier) {
            tiers.add(tier)
        }

        fun appendRange(tiersList: MutableList<InsectTier>) {
            for (tier in tiersList) {
                tiers.add(tier)
            }
        }

        fun valueOf(tierName: String): InsectTier {
            val tier = InsectTier(tierName)
            val invalidTier = InsectTier("INVALID")
            val isInvalid = !tiers.contains(tier)
            if (isInvalid) return invalidTier
            return tier
        }

        fun values(): MutableList<InsectTier> {
            return tiers
        }
    }
}
