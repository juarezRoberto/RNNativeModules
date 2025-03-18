package com.upax.zemytalents.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.entity.talentscompleted.ZEMTTalentsCompletedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZEMTTalentsCompletedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTalentCompleted(talentCompleted: ZEMTTalentsCompletedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTalentsCompleted(talentsCompleted: List<ZEMTTalentsCompletedEntity>)

    @Query("SELECT * FROM talents_completed WHERE collaborator_id = :collaboratorId")
    suspend fun getTalentsCompletedById(collaboratorId: String): ZEMTTalentsCompletedEntity?

    @Query("SELECT * FROM talents_completed WHERE collaborator_id = :collaboratorId")
    fun getTalentsCompletedByIdFlow(collaboratorId: String): Flow<ZEMTTalentsCompletedEntity?>

    @Query("SELECT * FROM talents_completed WHERE collaborator_id in (:collaboratorIdList)")
    suspend fun getTalentsCompletedList(collaboratorIdList: List<String>): List<ZEMTTalentsCompletedEntity>
}