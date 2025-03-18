package com.upax.zemytalents.data.repository.fake

import com.upax.zemytalents.domain.repositories.ZEMTDeviceRepository
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil

class ZEMTFakeDeviceRepository(
    override val name: String = ZEMTRandomValuesUtil.getString(),
    override val platform: String = ZEMTRandomValuesUtil.getString(),
): ZEMTDeviceRepository