package com.upax.zemytalents.domain.models

data class ZEMTTalentAnimationData(
    val talentId: Int,
    val talentName: String,
    val isTemp: Boolean,
    val order: Int,
    val icon: Int
)