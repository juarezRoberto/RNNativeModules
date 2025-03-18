package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTBreakDiscoverEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZEMTBreakDiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breaks: List<ZEMTBreakDiscoverEntity>)

    @Query(
        "    SELECT * \n" +
                "    FROM SurveyDiscoverBreak \n" +
                "    WHERE seen = 0 \n" +
                "    ORDER BY ordenAgrupamiento ASC \n" +
                "    LIMIT 1"
    )
    fun getNextBreak(): Flow<ZEMTBreakDiscoverEntity?>

    @Query("UPDATE SurveyDiscoverBreak SET seen = 1 WHERE ordenAgrupamiento = :groupQuestionIndex")
    suspend fun updateSeenBreak(groupQuestionIndex: Int)

}