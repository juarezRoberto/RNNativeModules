package com.upax.zemytalents.common

/** Returns the Int if it is not null, or 0 otherwise. */
internal fun Int?.orZero(): Int = this ?: 0

/** Returns the Int if it is not null, or 1 otherwise. */
internal fun Int?.orOne(): Int = this ?: 1

/** Returns the Float if it is not null, or 0f otherwise. */
internal fun Float?.orZero(): Float = this ?: 0f

/** Returns the Long if it is not null, or 0 otherwise. */
internal fun Long?.orZero(): Long = this ?: 0L

/** Returns the Boolean if it is not null, or false otherwise. */
internal fun Boolean?.orFalse(): Boolean = this ?: false