package com.upax.zemytalents.domain.repositories

internal interface ZEMTDateRepository {

    fun currentDate(): String

    companion object {
        internal const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

}