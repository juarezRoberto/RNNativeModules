package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository

internal abstract class ZEMTLocationUpdater(
    private val locationRepository: ZEMTLocationRepository
) {

    suspend fun updateLocation() {
        getLocation()?.let { locationRepository.updateLocation(it) }
    }

    protected abstract suspend fun getLocation(): ZEMTLocation?

}