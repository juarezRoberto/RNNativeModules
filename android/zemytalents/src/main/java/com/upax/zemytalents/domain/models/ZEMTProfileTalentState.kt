package com.upax.zemytalents.domain.models

sealed interface ZEMTProfileTalentState {
    data object Start : ZEMTProfileTalentState
    data object Done : ZEMTProfileTalentState
    data class Label(val talent: String, val isTheLastTalent: Boolean) : ZEMTProfileTalentState
}