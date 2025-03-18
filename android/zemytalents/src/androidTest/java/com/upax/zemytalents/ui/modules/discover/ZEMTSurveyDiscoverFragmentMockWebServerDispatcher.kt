package com.upax.zemytalents.ui.modules.discover

import com.upax.zemytalents.data.repository.test.ZEMTStubUserRepository
import com.upax.zemytalents.rules.SurveyApplyDatabaseRule
import com.upax.zemytalents.rules.SurveyDiscoverDatabaseRule
import com.upax.zemytalents.utils.fromJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ZEMTSurveyDiscoverFragmentMockWebServerDispatcher: Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/talents/surveys/${SurveyDiscoverDatabaseRule.SURVEY_DISCOVER_ID}?collaborator_id=${ZEMTStubUserRepository.COLLABORATOR_ID}" -> {
                MockResponse().fromJson("survey-discover-success.json")
            }
            else -> MockResponse().setResponseCode(404)
        }
    }

}