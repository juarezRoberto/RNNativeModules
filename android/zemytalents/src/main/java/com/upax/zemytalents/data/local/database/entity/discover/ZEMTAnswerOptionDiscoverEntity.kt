package com.upax.zemytalents.data.local.database.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyDiscoverAnswerOption")
data class ZEMTAnswerOptionDiscoverEntity(
    @PrimaryKey
    @ColumnInfo(name = "answerOptionId") val answerOptionId: Int,
    @ColumnInfo(name = "questionId") val questionId: Int,
    @ColumnInfo(name = "position") val order: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "value") val value: Int
)
