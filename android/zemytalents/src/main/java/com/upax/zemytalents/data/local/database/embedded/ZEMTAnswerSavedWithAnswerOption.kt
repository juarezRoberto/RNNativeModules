package com.upax.zemytalents.data.local.database.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerSavedDiscoverEntity

data class ZEMTAnswerSavedWithAnswerOption(
    @Embedded val answerSaved: ZEMTAnswerSavedDiscoverEntity,
    @Relation(
        parentColumn = "answerOptionId",
        entityColumn = "answerOptionId"
    )
    val answerOption: ZEMTAnswerOptionDiscoverEntity
)