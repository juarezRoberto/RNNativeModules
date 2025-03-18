package com.upax.zemytalents.data.local.database.entity.discover

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "SurveyDiscoverAnswerSaved",
    primaryKeys = [
        ZEMTAnswerSavedDiscoverEntity.COLUMN_NAME_QUESTION_ID,
        ZEMTAnswerSavedDiscoverEntity.COLUMN_NAME_ANSWER_OPTION_ID
    ]
)
data class ZEMTAnswerSavedDiscoverEntity(
    @ColumnInfo(name = COLUMN_NAME_QUESTION_ID) val questionId: Int,
    @ColumnInfo(name = COLUMN_NAME_ANSWER_OPTION_ID) val answerOptionId: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "value") val value: Int,
    @ColumnInfo(name = "ordenAgrupamiento") val groupQuestionIndex: Int,

) {
    companion object {
        const val COLUMN_NAME_QUESTION_ID = "questionId"
        const val COLUMN_NAME_ANSWER_OPTION_ID = "answerOptionId"
    }
}
