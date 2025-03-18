package com.upax.zemytalents.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import kotlinx.coroutines.tasks.await
import java.lang.Exception

internal class ZEMTLocationUpdaterImpl(
    val context: Context,
    locationRepository: ZEMTLocationRepository
): ZEMTLocationUpdater(locationRepository) {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): ZEMTLocation? {
        val location: Location? = try {
            val client: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            client.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).await()
        } catch (e: Exception) {
            null
        }
        if (location == null) return null
        return ZEMTLocation(
            latitude = location.latitude.toString(),
            longitude = location.longitude.toString()
        )
    }

}