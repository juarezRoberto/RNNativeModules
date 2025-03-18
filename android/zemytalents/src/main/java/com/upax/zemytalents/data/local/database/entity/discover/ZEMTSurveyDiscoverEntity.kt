package com.upax.zemytalents.data.local.database.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyDiscover")
data class ZEMTSurveyDiscoverEntity(
    @PrimaryKey
    @ColumnInfo(name = "surveyId") val surveyId: Int,
    @ColumnInfo(name = "ordenAgrupamientoProgress") val groupQuestionIndex: Int,
)
