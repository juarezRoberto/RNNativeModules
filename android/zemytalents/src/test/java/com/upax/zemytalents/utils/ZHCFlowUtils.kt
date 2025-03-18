package com.upax.zemytalents.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop

internal object ZHCFlowUtils {
    internal fun <T> Flow<T>.avoidFirstUiStateEvent(): Flow<T> = drop(1)
}