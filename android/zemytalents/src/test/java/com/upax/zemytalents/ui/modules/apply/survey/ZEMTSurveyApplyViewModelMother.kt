package com.upax.zemytalents.ui.modules.apply.survey

import com.upax.zemytalents.data.repository.fake.ZEMTFakeDateRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeLocationRepository
import com.upax.zemytalents.data.repository.fake.ZEMTDummySurveyApplyAnswersRepository
import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.ZEMTTalentsMother
import com.upax.zemytalents.domain.models.modules.ZEMTSurveyTalent
import com.upax.zemytalents.domain.usecases.ZEMTGetMyUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyUseCase
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSaveSurveyApplyAnswerUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeApplyAnswersUseCase
import com.upax.zemytalents.ui.modules.apply.survey.mapper.ZEMTTalentsApplyToUiModelMapper
import io.mockk.coEvery
import io.mockk.mockk

internal object ZEMTSurveyApplyViewModelMother {

    fun apply(
        getSurveyApplyUseCase: ZEMTGetSurveyApplyUseCase = mockk(),
        getUserTalentsUseCase: ZEMTGetMyUserTalentsUseCase = mockk(),
        surveyTalentsToUiModelMapper: ZEMTTalentsApplyToUiModelMapper = ZEMTTalentsApplyToUiModelMapper(),
        synchronizeAnswersUseCase: ZEMTSynchronizeApplyAnswersUseCase = mockk(),
        saveSurveyApplyUseCase: ZEMTSaveSurveyApplyAnswerUseCase = ZEMTSaveSurveyApplyAnswerUseCase(
            ZEMTDummySurveyApplyAnswersRepository(),
            ZEMTFakeLocationRepository(),
            ZEMTFakeDateRepository()
        ),
        locationUpdater: ZEMTLocationUpdater = mockk()
    ): ZEMTSurveyApplyViewModel {
        return ZEMTSurveyApplyViewModel(
            getSurveyApplyUseCase = getSurveyApplyUseCase,
            getUserTalentsUseCase = getUserTalentsUseCase,
            surveyTalentsToUiModelMapper = surveyTalentsToUiModelMapper,
            synchronizeAnswersUseCase = synchronizeAnswersUseCase,
            saveSurveyApplyUseCase = saveSurveyApplyUseCase,
            locationUpdater = locationUpdater
        )
    }

    fun random() = apply()

    fun createCommonViewModelInstance(
        surveyTalents: List<ZEMTSurveyTalent> = emptyList(),
        userTalents: ZEMTTalents = ZEMTTalentsMother.random(),
        synchronizeAnswersUseCase: ZEMTSynchronizeApplyAnswersUseCase = mockk(),
    ): ZEMTSurveyApplyViewModel {
        return apply(
            getSurveyApplyUseCase = mockk<ZEMTGetSurveyApplyUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(surveyTalents)
            },
            getUserTalentsUseCase = mockk<ZEMTGetMyUserTalentsUseCase> {
                coEvery { this@mockk.invoke() } returns ZEMTResult.Success(userTalents)
            },
            synchronizeAnswersUseCase = synchronizeAnswersUseCase
        )
    }

}