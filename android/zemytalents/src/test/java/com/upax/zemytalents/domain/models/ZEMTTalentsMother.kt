package com.upax.zemytalents.domain.models

import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

internal object ZEMTTalentsMother {

    fun random(
        dominantTalents: List<ZEMTTalent> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTTalentMother.random() },
        notDominantTalents: List<ZEMTTalent> = ZEMTRandomValuesUtil.getRandomIntRange()
            .map { ZEMTTalentMother.random() }
    ): ZEMTTalents {
        return ZEMTTalents(
            dominantTalents = dominantTalents,
            notDominantTalents = notDominantTalents
        )
    }

    fun apply() = random()

}