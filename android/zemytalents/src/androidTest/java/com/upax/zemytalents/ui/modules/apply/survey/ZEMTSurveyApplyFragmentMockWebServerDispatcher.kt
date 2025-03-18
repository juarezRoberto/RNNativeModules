package com.upax.zemytalents.ui.modules.apply.survey

import com.upax.zemytalents.data.repository.test.ZEMTStubUserRepository
import com.upax.zemytalents.rules.SurveyApplyDatabaseRule
import com.upax.zemytalents.utils.fromJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ZEMTSurveyApplyFragmentMockWebServerDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/talents/surveys/${SurveyApplyDatabaseRule.SURVEY_APPLY_ID}/secondary-modules?collaborator_id=${ZEMTStubUserRepository.COLLABORATOR_ID}" -> {
                MockResponse().fromJson("survey-apply-success.json")
            }

            "/talents/summary?collaborator-id=${ZEMTStubUserRepository.COLLABORATOR_ID}" -> {
                MockResponse().fromJson("summary-talents-success.json")
            }

            else -> MockResponse().setResponseCode(404)
        }
    }

    companion object {
        const val TOTAL_SURVEY_TALENTS = 5
        const val TOTAL_ANSWERS_TALENTS = 5
        val NAME_TALENTS_IN_ORDER = listOf(
            "Competidor", "Articulador", "Conquistador", "Examinador", "Encanto"
        )
    }

}