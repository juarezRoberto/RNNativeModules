package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.apply.ZEMTSurveyApplyAnswerSavedEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ZEMTSurveyApplyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(survey: ZEMTSurveyApplyAnswerSavedEntity)

    @Query("SELECT * FROM SurveyApplyAnswerSaved")
    suspend fun getAllAnswers(): List<ZEMTSurveyApplyAnswerSavedEntity>

    @Query("SELECT COUNT(*) FROM SurveyApplyAnswerSaved")
    fun getTotalAnswers(): Flow<Int>

    @Query("DELETE FROM SurveyApplyAnswerSaved")
    suspend fun deleteAll()

}