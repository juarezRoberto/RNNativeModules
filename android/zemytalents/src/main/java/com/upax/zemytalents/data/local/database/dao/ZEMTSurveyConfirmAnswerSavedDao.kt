package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.confirm.ZEMTSurveyConfirmAnswerSavedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZEMTSurveyConfirmAnswerSavedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: ZEMTSurveyConfirmAnswerSavedEntity)

    @Query("SELECT * FROM SurveyConfirmAnswerSaved")
    suspend fun getSavedAnswers(): List<ZEMTSurveyConfirmAnswerSavedEntity>

    @Query("SELECT * FROM SurveyConfirmAnswerSaved")
    fun collectSavedAnswers(): Flow<List<ZEMTSurveyConfirmAnswerSavedEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM SurveyConfirmAnswerSaved)")
    suspend fun hasSavedAnswers(): Boolean

    @Query("SELECT * FROM SurveyConfirmAnswerSaved WHERE id = (SELECT MAX(id) FROM SurveyConfirmAnswerSaved)")
    suspend fun getLatestSavedAnswer(): ZEMTSurveyConfirmAnswerSavedEntity

    @Query("DELETE FROM SurveyConfirmAnswerSaved WHERE talentId = :id")
    suspend fun deleteAnswersByTalentId(id: Int)

    @Query("DELETE FROM SurveyConfirmAnswerSaved")
    suspend fun deleteAllSavedAnswers()
}