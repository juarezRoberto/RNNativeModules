package com.upax.zemytalents.data.local.database.entity.confirm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyConfirmAnswerSaved")
data class ZEMTSurveyConfirmAnswerSavedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, //auto increment
    val answerId: Int,
    val answerOrder: Int,
    val questionId: Int,
    val questionOrder: Int,
    val talentId: Int,
    val talentOrder: Int,
    val date: String,
    val latitude: String,
    val longitude: String
)