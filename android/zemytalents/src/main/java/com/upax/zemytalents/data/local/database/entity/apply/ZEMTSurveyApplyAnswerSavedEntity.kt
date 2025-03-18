package com.upax.zemytalents.data.local.database.entity.apply

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upax.zemytalents.domain.models.modules.apply.ZEMTSurveyApplyAnswerStatus

@Entity(tableName = "SurveyApplyAnswerSaved")
internal data class ZEMTSurveyApplyAnswerSavedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val answerId: Int,
    val questionId: Int,
    val status: ZEMTSurveyApplyAnswerStatus,
    val latitude: String,
    val longitude: String,
    val date: String
)