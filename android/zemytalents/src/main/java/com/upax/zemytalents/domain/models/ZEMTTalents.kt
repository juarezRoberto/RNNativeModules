package com.upax.zemytalents.domain.models

internal data class ZEMTTalents(
    val dominantTalents: List<ZEMTTalent>,
    val notDominantTalents: List<ZEMTTalent>
)
