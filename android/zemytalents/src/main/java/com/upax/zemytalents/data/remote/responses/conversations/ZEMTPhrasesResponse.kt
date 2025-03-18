package com.upax.zemytalents.data.remote.responses.conversations

import com.google.gson.annotations.SerializedName
import com.upax.zemytalents.data.remote.responses.ZEMTTalentResponse

internal data class ZEMTPhrasesResponse(
    @SerializedName("phrases")
    val phrases: List<ZEMTPhraseResponse>
)

/**
 * Response for a single phrase
 * @property id id of the phrase
 * @property description description of the phrase
 * @property associateTalent talent associated with the phrase, even if it uses [ZEMTTalentResponse], it only retrieves the id and description
* */
internal data class ZEMTPhraseResponse(
    @SerializedName("phrase_id") val id: String,
    @SerializedName("description") val phrase: String,
)
