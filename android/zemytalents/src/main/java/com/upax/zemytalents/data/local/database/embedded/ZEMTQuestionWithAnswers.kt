package com.upax.zemytalents.data.local.database.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity

internal data class ZEMTQuestionWithAnswers(
    @Embedded val question: ZEMTQuestionDiscoverEntity,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val answers: List<ZEMTAnswerOptionDiscoverEntity>
)
