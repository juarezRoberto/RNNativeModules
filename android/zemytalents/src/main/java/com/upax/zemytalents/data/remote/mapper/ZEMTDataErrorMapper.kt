package com.upax.zemytalents.data.remote.mapper

import com.upax.zcservicecoordinator.expose.models.ZCSCError
import com.upax.zemytalents.domain.models.error.ZEMTDataError

internal fun <T> ZCSCError<T>.toDataError(): ZEMTDataError {
    return if (this.isConnectivityError) {
        ZEMTDataError.NetworkError(this.message)
    } else {
        ZEMTDataError.ServerError(this.message)
    }
}