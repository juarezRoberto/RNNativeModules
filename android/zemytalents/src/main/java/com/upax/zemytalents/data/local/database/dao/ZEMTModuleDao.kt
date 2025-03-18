package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ZEMTModuleDao {

    @Query("SELECT * FROM module WHERE stage = :moduleStage")
    suspend fun getModuleByStage(moduleStage: String): ZEMTModuleEntity?

    @Query("SELECT * FROM module ORDER BY `order` ASC")
    fun collectModulesByOrder(): Flow<List<ZEMTModuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ZEMTModuleEntity)

    @Query("UPDATE module SET is_complete = :isComplete WHERE survey_id = :surveyId")
    suspend fun updateCompleteStatus(surveyId: Int, isComplete: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ZEMTModuleEntity>)
}