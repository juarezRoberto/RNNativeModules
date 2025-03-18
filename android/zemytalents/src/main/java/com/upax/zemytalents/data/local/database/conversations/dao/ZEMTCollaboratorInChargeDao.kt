package com.upax.zemytalents.data.local.database.conversations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upax.zemytalents.data.local.database.conversations.entity.ZEMTCollaboratorInChargeEntity

@Dao
internal interface ZEMTCollaboratorInChargeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collaborators: List<ZEMTCollaboratorInChargeEntity>)

    @Query("SELECT * FROM collaborator_in_charge where leader_id = :leaderId")
    fun getCollaboratorsInCharge(leaderId: String): List<ZEMTCollaboratorInChargeEntity>

    @Query("SELECT * FROM collaborator_in_charge WHERE name LIKE :name COLLATE NOCASE ORDER BY name ASC")
    suspend fun findCollaboratorsByName(name: String): List<ZEMTCollaboratorInChargeEntity>

}