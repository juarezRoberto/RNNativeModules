package com.upax.zemytalents.domain.models

import com.upax.zemytalents.domain.models.error.ZEMTDomainError

internal sealed interface ZEMTResult<out T, out E: ZEMTDomainError> {
    data class Success<out T>(val data: T) : ZEMTResult<T, Nothing>
    data class Error<out E : ZEMTDomainError>(val error: E) : ZEMTResult<Nothing, E>
}

internal inline fun <T, E, R> ZEMTResult<T, E>.flatMap(
    transform: (T) -> ZEMTResult<R, E>
): ZEMTResult<R, E> where E : ZEMTDomainError =
    when (this) {
        is ZEMTResult.Success -> transform(data)
        is ZEMTResult.Error -> this
    }

internal inline fun <T, E> ZEMTResult<T, E>.onSuccess(
    action: (T) -> Unit
): ZEMTResult<T, E> where E : ZEMTDomainError {
    if (this is ZEMTResult.Success) {
        action(data)
    }
    return this
}

internal fun <A, B, E : ZEMTDomainError> combineResults(
    result1: ZEMTResult<A, E>,
    result2: ZEMTResult<B, E>
): ZEMTResult<Pair<A, B>, E> {
    return when {
        result1 is ZEMTResult.Success && result2 is ZEMTResult.Success -> {
            ZEMTResult.Success(result1.data to result2.data)
        }
        result1 is ZEMTResult.Error -> result1
        result2 is ZEMTResult.Error -> result2
        else -> error("Unexpected case")
    }
}