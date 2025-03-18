package com.upax.zemytalents.data.repository

import com.prometeo.keymanager.preferences.ZKMPreferences
import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository

internal class ZEMTKeyManagerPreferencesLocationRepository(
    private val preferences: ZKMPreferences
): ZEMTLocationRepository {

    override fun getLocation(): ZEMTLocation {
        val latitude = preferences.getString(KEY_LATITUDE, String.EMPTY)
        val longitude = preferences.getString(KEY_LONGITUDE, String.EMPTY)
        return ZEMTLocation(latitude = latitude, longitude = longitude)
    }

    override fun updateLocation(location: ZEMTLocation) {
        preferences.put(KEY_LATITUDE, location.latitude)
        preferences.put(KEY_LONGITUDE, location.longitude)
    }

    companion object {
        private const val KEY_LATITUDE = "KEY_LATITUDE"
        private const val KEY_LONGITUDE = "KEY_LONGITUDE"
    }

}