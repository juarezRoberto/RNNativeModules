package com.upax.zemytalents.domain.models.error

internal sealed class ZEMTDataError(val message: String): ZEMTDomainError {
    class NetworkError(message: String) : ZEMTDataError(message)
    class ServerError(message: String): ZEMTDataError(message)
}