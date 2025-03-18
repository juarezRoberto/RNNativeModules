package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.embedded.ZEMTQuestionWithAnswers
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ZEMTQuestionDiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<ZEMTQuestionDiscoverEntity>)

    @Query("SELECT * FROM SurveyDiscoverQuestion as Q " +
            "WHERE q.ordenAgrupamiento = (SELECT s.ordenAgrupamientoProgress FROM SurveyDiscover as s LIMIT 1)" +
            "ORDER BY q.position ASC"
    )
    fun getCurrentGroupQuestions(): Flow<List<ZEMTQuestionWithAnswers>?>


    @Query("UPDATE SurveyDiscoverQuestion SET lastSeen = :lastSeen WHERE ordenAgrupamiento = :groupQuestions")
    suspend fun updateLastSeen(groupQuestions: Int, lastSeen: Long)

    @Query("SELECT * FROM SurveyDiscoverQuestion WHERE questionId = :questionId")
    suspend fun getQuestionById(questionId: Int): ZEMTQuestionDiscoverEntity

    @Query("SELECT q.ordenAgrupamiento FROM SurveyDiscoverQuestion as q\n" +
            "                        LEFT JOIN SurveyDiscoverAnswerSaved a\n" +
            "                        ON q.ordenAgrupamiento = a.ordenAgrupamiento\n" +
            "                        WHERE a.questionId IS NULL\n" +
            "                        GROUP BY q.ordenAgrupamiento\n" +
            "                        ORDER BY q.lastSeen ASC, q.ordenAgrupamiento ASC\n" +
            "                        LIMIT 1")
    suspend fun getNextGroupQuestionIndex(): Int?

    @Query("SELECT MAX(ordenAgrupamiento) FROM SurveyDiscoverQuestion as Q ")
    suspend fun getTotalGroupQuestions(): Int

}