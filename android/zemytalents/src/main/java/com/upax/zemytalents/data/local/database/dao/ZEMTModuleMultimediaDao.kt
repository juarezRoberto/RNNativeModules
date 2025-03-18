package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.modules.ZEMTModuleMultimediaEntity

@Dao
interface ZEMTModuleMultimediaDao {
    @Query("SELECT * FROM module_multimedia WHERE module_id = :moduleId")
    suspend fun getMultimediaByModuleId(moduleId: Int): List<ZEMTModuleMultimediaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(multimediaEntity: List<ZEMTModuleMultimediaEntity>)
}