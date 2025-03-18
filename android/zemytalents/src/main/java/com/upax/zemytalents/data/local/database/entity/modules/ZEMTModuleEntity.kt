package com.upax.zemytalents.data.local.database.entity.modules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module")
data class ZEMTModuleEntity(
    @PrimaryKey
    @ColumnInfo(name = "module_id") val moduleId: Int,
    @ColumnInfo(name = "stage") val stage: String,
    @ColumnInfo(name = "survey_id") val surveyId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_complete") val isComplete: Boolean
)
