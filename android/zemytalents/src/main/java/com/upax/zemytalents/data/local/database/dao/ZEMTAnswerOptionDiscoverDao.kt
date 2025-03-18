package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity

@Dao
interface ZEMTAnswerOptionDiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<ZEMTAnswerOptionDiscoverEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answers: ZEMTAnswerOptionDiscoverEntity)

    @Query("SELECT * FROM SurveyDiscoverAnswerOption WHERE answerOptionId = :id")
    suspend fun getAnswerOptionById(id: Int): ZEMTAnswerOptionDiscoverEntity

    @Query("SELECT COUNT(*) FROM surveydiscoveransweroption")
    suspend fun getTotalAnswers(): Int

}