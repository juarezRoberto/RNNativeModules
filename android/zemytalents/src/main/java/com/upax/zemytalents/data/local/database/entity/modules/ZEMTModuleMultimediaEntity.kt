package com.upax.zemytalents.data.local.database.entity.modules

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "module_multimedia",
    primaryKeys = [
        ZEMTModuleMultimediaEntity.COLUMN_NAME_MODULE_ID,
        ZEMTModuleMultimediaEntity.COLUMN_NAME_ORDER
    ]
)
data class ZEMTModuleMultimediaEntity(
    @ColumnInfo(name = "module_id") val moduleId: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url_thumbnail") val urlThumbnail: String
) {
    companion object {
        const val COLUMN_NAME_MODULE_ID = "module_id"
        const val COLUMN_NAME_ORDER = "order"
    }
}
