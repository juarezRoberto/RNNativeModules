package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

class ZEMTFakeLocationRepository : ZEMTLocationRepository {

    private var location = ZEMTLocation(
        latitude = ZEMTRandomValuesUtil.getString(),
        longitude = ZEMTRandomValuesUtil.getString()
    )

    override fun getLocation(): ZEMTLocation {
        return location
    }

    override fun updateLocation(location: ZEMTLocation) {
        this.location = location
    }

}