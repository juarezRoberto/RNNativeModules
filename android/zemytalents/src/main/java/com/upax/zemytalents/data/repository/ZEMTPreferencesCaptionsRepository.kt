package com.upax.zemytalents.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prometeo.keymanager.preferences.ZKMPreferences
import com.upax.zemytalents.domain.models.ZEMTCaption
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsFinder
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsStorage

internal class ZEMTPreferencesCaptionsRepository(
    private val preferences: ZKMPreferences
) : ZEMTCaptionsStorage, ZEMTCaptionsFinder {

    override fun save(captions: List<ZEMTCaption>) {
        preferences.put(KEY_PREFERENCES_CAPTIONS, Gson().toJson(captions))
    }

    override fun findById(id: Int): ZEMTCaption? {
        val gsonCaptions = preferences.getString(KEY_PREFERENCES_CAPTIONS)
        val captionsType = object : TypeToken<List<ZEMTCaption>>() {}.type
        val captions = Gson().fromJson<List<ZEMTCaption>>(gsonCaptions, captionsType)
        return captions.find { it.sectionId == id }
    }

    companion object {
        private const val KEY_PREFERENCES_CAPTIONS = "KEY_PREFERENCES_CAPTIONS"
    }

}