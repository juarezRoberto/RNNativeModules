package com.upax.zemytalents.data.repository

import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.domain.repositories.ZEMTDateRepository.Companion.DATE_FORMAT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ZEMTLocalDateTimeDateRepositoryImpl : ZEMTDateRepository {

    override fun currentDate(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return currentDateTime.format(formatter)
    }

}