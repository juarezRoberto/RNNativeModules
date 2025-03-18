package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

class ZEMTFakeDateRepository: ZEMTDateRepository {

    private var date = ZEMTRandomValuesUtil.getString()

    override fun currentDate(): String {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }

}