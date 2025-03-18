package com.upax.zemytalents.data.repository

import android.os.Build
import com.upax.zemytalents.domain.repositories.ZEMTDeviceRepository

class ZEMTAndroidDeviceRepository: ZEMTDeviceRepository {
    override val name: String
        get() = Build.MODEL

    override val platform: String
        get() = "ANDROID"
}