package com.upax.zemytalents.data.local.database.entity.talentscompleted

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "talents_completed")
data class ZEMTTalentsCompletedEntity(
    @PrimaryKey @ColumnInfo(name = "collaborator_id") val collaboratorId: String,
    val completed: Boolean
)
