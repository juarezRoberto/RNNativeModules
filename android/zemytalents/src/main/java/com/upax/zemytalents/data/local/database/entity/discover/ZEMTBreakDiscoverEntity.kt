package com.upax.zemytalents.data.local.database.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyDiscoverBreak")
data class ZEMTBreakDiscoverEntity(
    @PrimaryKey
    @ColumnInfo(name = "ordenAgrupamiento") val groupQuestionIndex: Int,
    @ColumnInfo(name = "surveyId") val surveyId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "seen") val seen: Boolean,
)