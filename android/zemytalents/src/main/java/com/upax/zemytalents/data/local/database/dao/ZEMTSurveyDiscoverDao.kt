package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity

@Dao
internal interface ZEMTSurveyDiscoverDao {

    @Query("SELECT * FROM SurveyDiscover LIMIT 1")
    suspend fun getSurvey(): ZEMTSurveyDiscoverEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDiscoverSurvey(survey: ZEMTSurveyDiscoverEntity)

    @Query("SELECT ordenAgrupamientoProgress FROM SurveyDiscover LIMIT 1")
    suspend fun getCurrentGroupQuestionIndex(): Int

    @Query("UPDATE SurveyDiscover SET ordenAgrupamientoProgress = :index")
    suspend fun updateCurrentGroupQuestionIndex(index: Int)

}