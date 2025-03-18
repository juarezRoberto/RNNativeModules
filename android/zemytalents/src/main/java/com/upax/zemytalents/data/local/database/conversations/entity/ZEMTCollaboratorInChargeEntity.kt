package com.upax.zemytalents.data.local.database.conversations.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge

@Entity(tableName = "collaborator_in_charge")
internal data class ZEMTCollaboratorInChargeEntity(
    @PrimaryKey @ColumnInfo(name = "collaborator_id") val collaboratorId: String,
    @ColumnInfo(name = "leader_id") val leaderId: String,
    val name: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "talents_completed") val talentsCompleted: Boolean
) {
    fun toModel(): ZEMTCollaboratorInCharge {
        return ZEMTCollaboratorInCharge(collaboratorId, name, photoUrl, talentsCompleted)
    }
}