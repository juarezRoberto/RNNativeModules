package com.upax.zemytalents.data

import android.annotation.SuppressLint
import android.content.Context
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationServices
import com.upax.zemytalents.domain.models.ZEMTLocation
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class ZEMTLocationUpdaterImpl(
    val context: Context,
    locationRepository: ZEMTLocationRepository
): ZEMTLocationUpdater(locationRepository) {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): ZEMTLocation? {
        return suspendCoroutine { continuation ->
            try {
                val client: FusedLocationProviderClient? =
                    LocationServices.getFusedLocationProviderClient(context)
                client!!.lastLocation!!.addOnSuccessListener {
                    continuation.resume(
                        ZEMTLocation(
                            latitude = it?.latitude?.toString().orEmpty(),
                            longitude = it?.longitude?.toString().orEmpty()
                        )
                    )
                }?.addOnFailureListener {
                    continuation.resume(null)
                }
            } catch (e: Exception) {
                continuation.resume(null)
            }
        }
    }

}