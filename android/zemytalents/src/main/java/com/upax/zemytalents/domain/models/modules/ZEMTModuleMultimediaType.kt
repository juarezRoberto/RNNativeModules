package com.upax.zemytalents.domain.models.modules

internal enum class ZEMTModuleMultimediaType(val id: Int) {
    UNKNOWN(0),
    VIDEO(1),
    IMAGE(2);

    companion object {
        fun fromId(id: Int): ZEMTModuleMultimediaType {
            return entries.find { it.id == id } ?: UNKNOWN
        }
    }
}