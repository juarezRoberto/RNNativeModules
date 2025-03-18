package com.upax.zemytalents.ui.shared.composables.talentsprofile

import com.upax.zemytalents.domain.models.ZEMTTalent

internal sealed interface ZEMTTalentsProfileStages {
    data object Discover : ZEMTTalentsProfileStages
    data class Confirm(val temporalTalents: List<ZEMTTalent>) : ZEMTTalentsProfileStages
    data class ConfirmNoAnimation(val temporalTalents: List<ZEMTTalent>) :
        ZEMTTalentsProfileStages

    data class Apply(val talents: List<ZEMTTalent>) : ZEMTTalentsProfileStages
    data class Complete(val talents: List<ZEMTTalent>) : ZEMTTalentsProfileStages
}