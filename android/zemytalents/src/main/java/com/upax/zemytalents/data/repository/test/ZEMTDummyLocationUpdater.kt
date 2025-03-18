package com.upax.zemytalents.data.repository.test

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater

internal val DummyLocationRepository: ZEMTLocationRepository by lazy {
    object : ZEMTLocationRepository {
        override fun getLocation(): ZEMTLocation {
            return ZEMTLocation("", "")
        }

        override fun updateLocation(location: ZEMTLocation) { }
    }
}

internal class ZEMTDummyLocationUpdater : ZEMTLocationUpdater(DummyLocationRepository) {

    override suspend fun getLocation(): ZEMTLocation? {
        return null
    }

}