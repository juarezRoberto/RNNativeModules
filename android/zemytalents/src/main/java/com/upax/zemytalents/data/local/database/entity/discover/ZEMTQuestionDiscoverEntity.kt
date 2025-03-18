package com.upax.zemytalents.data.local.database.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyDiscoverQuestion")
data class ZEMTQuestionDiscoverEntity(
    @PrimaryKey
    @ColumnInfo(name = "questionId") val questionId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "surveyId") val surveyId: Int,
    @ColumnInfo(name = "position") val order: Int,
    @ColumnInfo(name = "ordenAgrupamiento") val groupQuestionIndex: Int,
    @ColumnInfo(name = "lastSeen") val lastSeen: Long,
)