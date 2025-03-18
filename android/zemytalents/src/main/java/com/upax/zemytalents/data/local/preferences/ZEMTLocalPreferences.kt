package com.upax.zemytalents.data.local.preferences

import com.prometeo.keymanager.preferences.ZKMPreferences
import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.common.orZero

class ZEMTLocalPreferences(private val preferences: ZKMPreferences) {

    fun getInt(key: String): Int? = preferences.getInt(key, DEFAULT_INT).let { value ->
        return if (value == DEFAULT_INT) null else value
    }

    fun getString(key: String): String = preferences.getString(key, String.EMPTY)

    fun getBoolean(key: String, default: Boolean = DEFAULT_BOOLEAN): Boolean {
        return preferences.getBoolean(key, DEFAULT_BOOLEAN)
    }

    fun put(key: String, value: String) = preferences.put(key, value)

    fun put(key: String, value: Int) = preferences.put(key, value)

    fun put(key: String, value: Boolean) = preferences.put(key, value)

    fun clearAll() = preferences.clear()

    var wasIntroductionViewed: Boolean
        get() = getBoolean(Key.INTRODUCTION_VIEWED)
        set(value) {
            put(Key.INTRODUCTION_VIEWED, value)
        }

    var conversationTypesTipsShown: Boolean
        get() = getBoolean(Key.CONVERSATION_TYPES_TIPS_SHOWN)
        set(value) {
            put(Key.CONVERSATION_TYPES_TIPS_SHOWN, value)
        }

    var makeConversationOnboardingShown: Boolean
        get() = getBoolean(Key.MAKE_CONVERSATION_ONBOARDING_SHOWN)
        set(value) {
            put(Key.MAKE_CONVERSATION_ONBOARDING_SHOWN, value)
        }

    var confirmSurveyTotalQuestions: Int
        get() = getInt(Key.CONFIRM_SURVEY_TOTAL_QUESTIONS).orZero()
        set(value) {
            put(Key.CONFIRM_SURVEY_TOTAL_QUESTIONS, value)
        }

    companion object {
        const val FILE_NAME = "zemt_shared_preferences"
        private const val DEFAULT_BOOLEAN = false
        private const val DEFAULT_INT = 0
        private lateinit var INSTANCE: ZEMTLocalPreferences

        @JvmStatic
        fun getInstance(preferences: ZKMPreferences): ZEMTLocalPreferences {
            if (!Companion::INSTANCE.isInitialized) INSTANCE = ZEMTLocalPreferences(preferences)
            return INSTANCE
        }
    }

    object Key {
        const val INTRODUCTION_VIEWED = "INTRODUCTION_VIEWED"
        const val CONVERSATION_TYPES_TIPS_SHOWN = "CONVERSATION_TYPES_TIPS_SHOWN"
        const val MAKE_CONVERSATION_ONBOARDING_SHOWN = "MAKE_CONVERSATION_ONBOARDING_SHOWN"
        const val CONFIRM_SURVEY_TOTAL_QUESTIONS = "CONFIRM_SURVEY_TOTAL_QUESTIONS"
    }
}