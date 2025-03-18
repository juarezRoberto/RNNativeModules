package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.embedded.ZEMTAnswerSavedWithAnswerOption
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerSavedDiscoverEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZEMTAnswerSavedDiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: ZEMTAnswerSavedDiscoverEntity)

    @Query("SELECT COUNT(*) FROM surveydiscoveranswersaved WHERE value == 0")
    suspend fun getTotalNeutralAnswers(): Int

    @Query("SELECT COUNT(*) FROM surveydiscoveranswersaved")
    fun getTotalAnswers(): Flow<Int>

    @Query("SELECT * FROM SurveyDiscoverAnswerSaved")
    suspend fun getAll(): List<ZEMTAnswerSavedWithAnswerOption>

    @Query("DELETE FROM SurveyDiscoverAnswerSaved")
    suspend fun deleteAll()

}