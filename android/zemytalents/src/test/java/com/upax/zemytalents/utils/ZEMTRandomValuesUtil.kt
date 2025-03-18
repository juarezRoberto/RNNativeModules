package com.upax.zemytalents.utils

import kotlin.random.Random

internal object ZEMTRandomValuesUtil {

    fun getString(length: Int = 10): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getInt(min: Int = 0, max: Int = 100): Int {
        return Random.nextInt(from = min, until = max)
    }

    fun getBoolean(): Boolean {
        return Random.nextBoolean()
    }

    fun getRandomIntRange(min: Int = 0, max: Int = 100): IntRange {
        return (min..max)
    }

}