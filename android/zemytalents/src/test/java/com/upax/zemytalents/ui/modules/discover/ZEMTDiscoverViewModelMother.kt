package com.upax.zemytalents.ui.modules.discover

import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyDiscoverBreaksRepository
import com.upax.zemytalents.data.repository.fake.ZEMTFakeSurveyDownloader
import com.upax.zemytalents.data.repository.fake.ZEMTFakeGroupQuestionsDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverReminder
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverDownloader
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverBreaksRepository
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater
import com.upax.zemytalents.domain.usecases.ZEMTSaveAnswerDiscoverUserCase
import com.upax.zemytalents.domain.usecases.ZEMTSkipGroupQuestionsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersDiscoverUseCase
import com.upax.zemytalents.ui.modules.discover.survey.mapper.ZEMTDiscoverGroupQuestionsToUiModelMapper
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverViewModel
import io.mockk.mockk

internal object ZEMTDiscoverViewModelMother {

    fun apply(
        surveyGroupQuestionRepository: ZEMTGroupQuestionsDiscoverRepository = ZEMTFakeGroupQuestionsDiscoverRepository(),
        saveAnswerUseCase: ZEMTSaveAnswerDiscoverUserCase = mockk<ZEMTSaveAnswerDiscoverUserCase>(),
        surveyDownloader: ZEMTSurveyDiscoverDownloader = ZEMTFakeSurveyDownloader(),
        skipGroupQuestionsUseCase: ZEMTSkipGroupQuestionsUseCase = mockk<ZEMTSkipGroupQuestionsUseCase>(),
        groupQuestionMapper: ZEMTDiscoverGroupQuestionsToUiModelMapper = ZEMTDiscoverGroupQuestionsToUiModelMapper(),
        surveyReminder: ZEMTSurveyDiscoverReminder = mockk<ZEMTSurveyDiscoverReminder>(),
        synchronizeSurveyAnswersUseCase: ZEMTSynchronizeAnswersDiscoverUseCase = mockk<ZEMTSynchronizeAnswersDiscoverUseCase>(),
        breaksRepository: ZEMTSurveyDiscoverBreaksRepository = ZEMTFakeSurveyDiscoverBreaksRepository(),
        locationUpdater: ZEMTLocationUpdater = mockk()
    ): ZEMTSurveyDiscoverViewModel {
        return ZEMTSurveyDiscoverViewModel(
            groupQuestionsRepository = surveyGroupQuestionRepository,
            saveAnswerUseCase = saveAnswerUseCase,
            surveyDownloader = surveyDownloader,
            skipGroupQuestionsUseCase = skipGroupQuestionsUseCase,
            discoverGroupQuestionToUiModelMapper = groupQuestionMapper,
            surveyReminder = surveyReminder,
            locationUpdater = locationUpdater,
            breaksRepository = breaksRepository,
            synchronizeAnswersUseCase = synchronizeSurveyAnswersUseCase
        )
    }

    fun random() = apply()

}