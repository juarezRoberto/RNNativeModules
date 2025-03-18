package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.ZEMTLocation

internal interface ZEMTLocationRepository {

    fun getLocation(): ZEMTLocation

    fun updateLocation(location: ZEMTLocation)

}