package com.upax.zemytalents.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerOptionDiscoverToEntityMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerSavedDiscoverEntityToModelMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTListQuestionWithAnswersEntityToModelMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTQuestionWithAnswersEntityToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTCaptionsResponseMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTSurveyTalentsResponseToModelMapper
import com.upax.zemytalents.data.remote.requests.ZEMTSyncSurveyAnswersRequestBuilder
import com.upax.zemytalents.data.repository.ZEMTAndroidDeviceRepository
import com.upax.zemytalents.data.repository.ZEMTAnswersApplyInterceptorSurveyTalentsRepository
import com.upax.zemytalents.data.repository.ZEMTConversationsRepository
import com.upax.zemytalents.data.repository.ZEMTConversationsRepositoryImpl
import com.upax.zemytalents.data.repository.ZEMTInMemoryTalentsRepository
import com.upax.zemytalents.data.repository.ZEMTKeyManagerPreferencesLocationRepository
import com.upax.zemytalents.data.repository.ZEMTLocalDateTimeDateRepositoryImpl
import com.upax.zemytalents.data.repository.ZEMTLocalSurveyApplyAnswersRepository
import com.upax.zemytalents.data.repository.ZEMTModulesRepositoryImpl
import com.upax.zemytalents.data.repository.ZEMTPreferencesCaptionsRepository
import com.upax.zemytalents.data.repository.ZEMTRetrofitAnswersSynchronizer
import com.upax.zemytalents.data.repository.ZEMTRetrofitCaptionsDownloader
import com.upax.zemytalents.data.repository.ZEMTRetrofitSurveyTalentsRepository
import com.upax.zemytalents.data.repository.ZEMTRoomAnswerOptionDiscoverRepository
import com.upax.zemytalents.data.repository.ZEMTRoomAnswerSavedDiscoverRepository
import com.upax.zemytalents.data.repository.ZEMTRoomCollaboratorsRepository
import com.upax.zemytalents.data.repository.ZEMTRoomGroupQuestionsDiscoverRepository
import com.upax.zemytalents.data.repository.ZEMTRoomSurveyApplyRepository
import com.upax.zemytalents.data.repository.ZEMTRoomSurveyConfirmRepository
import com.upax.zemytalents.data.repository.ZEMTRoomSurveyDiscoverBreaksRepository
import com.upax.zemytalents.data.repository.ZEMTRoomSurveyDiscoverRepository
import com.upax.zemytalents.data.repository.ZEMTRoomTalentsCompletedRepository
import com.upax.zemytalents.data.repository.ZEMTTalentsRepositoryImpl
import com.upax.zemytalents.data.repository.ZEMTUserRepositoryImpl
import com.upax.zemytalents.data.repository.ZEMTWorkManagerSurveyDiscoverReminder
import com.upax.zemytalents.data.repository.test.DummyLocationRepository
import com.upax.zemytalents.data.repository.test.ZEMTStubUserRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswerOptionDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswerSavedDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTAnswersSynchronizer
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsFinder
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsStorage
import com.upax.zemytalents.domain.repositories.ZEMTCollaboratorsRepository
import com.upax.zemytalents.domain.repositories.ZEMTDateRepository
import com.upax.zemytalents.domain.repositories.ZEMTDeviceRepository
import com.upax.zemytalents.domain.repositories.ZEMTGroupQuestionsDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTLocationRepository
import com.upax.zemytalents.domain.repositories.ZEMTModulesRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyAnswersRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyApplyRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyConfirmRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverBreaksRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverReminder
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverRepository
import com.upax.zemytalents.domain.repositories.ZEMTSurveyTalentsRepository
import com.upax.zemytalents.domain.repositories.ZEMTTalentsCompletedRepository
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository
import com.upax.zemytalents.domain.usecases.ZEMTCaptionsDownloader

internal object ZEMTRepositoryProvider {

    @VisibleForTesting
    var isTestingEnvironment = false

    fun provideUserRepository(context: Context): ZEMTUserRepository {
        if (isTestingEnvironment) {
            return ZEMTStubUserRepository()
        }
        return ZEMTUserRepositoryImpl(
            ZEMTDataProvider.provideSessionInfo(context)
        )
    }

    fun provideGroupQuestionsDiscoverRepository(context: Context): ZEMTGroupQuestionsDiscoverRepository {
        return ZEMTRoomGroupQuestionsDiscoverRepository(
            surveyDao = ZEMTDataProvider.provideLocalDatabase(context).surveyDiscoverDao(),
            questionDao = ZEMTDataProvider.provideLocalDatabase(context).questionDiscoverDao(),
            answerSavedDao = ZEMTDataProvider.provideLocalDatabase(context)
                .answerSavedDiscoverDao(),
            listQuestionWithAnswersToModelMapper = ZEMTListQuestionWithAnswersEntityToModelMapper(
                ZEMTQuestionWithAnswersEntityToModelMapper()
            )
        )
    }

    fun provideAnswerSavedDiscoverRepository(context: Context): ZEMTAnswerSavedDiscoverRepository {
        val database = ZEMTDataProvider.provideLocalDatabase(context)
        return ZEMTRoomAnswerSavedDiscoverRepository(
            answerSavedDao = database.answerSavedDiscoverDao(),
            questionDao = database.questionDiscoverDao(),
            answerOptionMapper = ZEMTAnswerOptionDiscoverToEntityMapper(
                dateRepository = provideDateRepository(),
                locationRepository = provideLocationRepository(context),
            ),
            answerSavedMapper = ZEMTAnswerSavedDiscoverEntityToModelMapper()
        )
    }

    fun provideLocationRepository(context: Context): ZEMTLocationRepository {
        if (isTestingEnvironment) {
            return DummyLocationRepository
        }
        return ZEMTKeyManagerPreferencesLocationRepository(
            ZEMTDataProvider.provideKeyManagerPreferences(context)
        )
    }

    fun provideDateRepository(): ZEMTDateRepository {
        return ZEMTLocalDateTimeDateRepositoryImpl()
    }

    fun provideAnswerOptionDiscoverRepository(context: Context): ZEMTAnswerOptionDiscoverRepository {
        val database = ZEMTDataProvider.provideLocalDatabase(context)
        return ZEMTRoomAnswerOptionDiscoverRepository(
            answerOptionDao = database.answerOptionDiscoverDao()
        )
    }

    fun provideModulesRepository(context: Context): ZEMTModulesRepository {
        return ZEMTModulesRepositoryImpl(
            ZEMTDataProvider.provideMyTalentsApi(context),
            provideUserRepository(context),
            ZEMTDataProvider.provideLocalDatabase(context).moduleDao(),
            ZEMTDataProvider.provideLocalDatabase(context).moduleMultimediaDao(),
            ZEMTDataProvider.provideLocalDatabase(context).surveyDiscoverDao()
        )
    }

    fun provideConversationsRepository(
        context: Context,
        useAlternativeUrl: Boolean = false,
    ): ZEMTConversationsRepository {
        return ZEMTConversationsRepositoryImpl(
            api = ZEMTDataProvider.provideConversationsApi(context, useAlternativeUrl),
            db = ZEMTDataProvider.provideConversationsDatabase(context),
            sessionInfo = ZEMTDataProvider.provideSessionInfo(context)
        )
    }

    fun provideTalentsRepositoryImpl(context: Context, isTempUrl: Boolean): ZEMTTalentsRepository {
        return ZEMTTalentsRepositoryImpl(
            myTalentsApi = if (isTempUrl) ZEMTDataProvider.provideMyTalentsApi(context)
            else ZEMTDataProvider.provideMyTalentsApi(context),
            talentsCompletedDao = ZEMTDataProvider.provideLocalDatabase(context)
                .talentsCompletedDao()
        )
    }

    fun provideTalentsRepositoryWithCache(context: Context): ZEMTTalentsRepository {
        return ZEMTInMemoryTalentsRepository.getInstance(
            talentsRepository = provideTalentsRepositoryImpl(context, true),
            talentsCompletedDao = ZEMTDataProvider.provideLocalDatabase(context)
                .talentsCompletedDao()
        )
    }

    fun provideSurveyDiscoverReminder(context: Context): ZEMTSurveyDiscoverReminder {
        return ZEMTWorkManagerSurveyDiscoverReminder(
            workManager = WorkManager.getInstance(context),
            notificationManager = NotificationManagerCompat.from(context)
        )
    }

    fun provideAnswersSynchronizer(context: Context): ZEMTAnswersSynchronizer {
        return ZEMTRetrofitAnswersSynchronizer(
            api = ZEMTDataProvider.provideDiscoverModuleApiService(context),
            requestBuilder = ZEMTSyncSurveyAnswersRequestBuilder(
                deviceRepository = provideDeviceRepository()
            )
        )
    }

    fun provideDeviceRepository(): ZEMTDeviceRepository {
        return ZEMTAndroidDeviceRepository()
    }

    fun provideSurveyDiscoverRepository(context: Context): ZEMTSurveyDiscoverRepository {
        return ZEMTRoomSurveyDiscoverRepository(
            ZEMTDataProvider.provideLocalDatabase(context).surveyDiscoverDao()
        )
    }

    fun provideSurveyConfirmRepository(context: Context): ZEMTSurveyConfirmRepository {
        return ZEMTRoomSurveyConfirmRepository(
            ZEMTDataProvider.provideLocalDatabase(context).moduleDao(),
            ZEMTDataProvider.provideLocalDatabase(context).surveyConfirmAnswerSavedDao(),
            ZEMTDataProvider.provideLocalPreferences(context)
        )
    }

    fun provideTalentsRepository(context: Context): ZEMTSurveyTalentsRepository {
        return ZEMTRetrofitSurveyTalentsRepository(
            myTalentsApi = ZEMTDataProvider.provideMyTalentsApi(context),
            userRepository = provideUserRepository(context),
            surveyTalentsResponseMapper = ZEMTSurveyTalentsResponseToModelMapper()
        )
    }

    fun provideTalentsApplyRepository(context: Context): ZEMTSurveyTalentsRepository {
        return ZEMTAnswersApplyInterceptorSurveyTalentsRepository(
            surveyRepository = provideTalentsRepository(context),
            answersRepository = provideSurveyApplyAnswersRepository(context)
        )
    }

    fun provideSurveyApplyRepository(context: Context): ZEMTSurveyApplyRepository {
        return ZEMTRoomSurveyApplyRepository(
            ZEMTDataProvider.provideLocalDatabase(context).moduleDao()
        )
    }

    fun provideCaptionsDownloader(context: Context): ZEMTCaptionsDownloader {
        return ZEMTRetrofitCaptionsDownloader(
            captionsApi = ZEMTDataProvider.provideCaptionsApi(context),
            mapper = ZEMTCaptionsResponseMapper(),
            userRepository = provideUserRepository(context),
            captionsSaver = provideCaptionsStorage(context)
        )
    }

    private fun provideCaptionsStorage(context: Context): ZEMTCaptionsStorage {
        return provideCaptionsRepository(context)
    }

    fun provideCaptionsFinder(context: Context): ZEMTCaptionsFinder {
        return provideCaptionsRepository(context)
    }

    private fun provideCaptionsRepository(context: Context): ZEMTPreferencesCaptionsRepository {
        return ZEMTPreferencesCaptionsRepository(
            preferences = ZEMTDataProvider.provideKeyManagerPreferences(context)
        )
    }

    fun provideSurveyApplyAnswersRepository(context: Context): ZEMTSurveyApplyAnswersRepository {
        return ZEMTLocalSurveyApplyAnswersRepository(
            dao = ZEMTDataProvider.provideLocalDatabase(context).surveyApplyDao(),
            preferences = ZEMTDataProvider.provideKeyManagerPreferences(context)
        )
    }

    fun provideTalentsCompletedRepository(context: Context): ZEMTTalentsCompletedRepository {
        return ZEMTRoomTalentsCompletedRepository(
            ZEMTDataProvider.provideLocalDatabase(context).talentsCompletedDao()
        )
    }

    fun provideSurveyDiscoverBreaksRepository(context: Context): ZEMTSurveyDiscoverBreaksRepository {
        return ZEMTRoomSurveyDiscoverBreaksRepository(
            breakDao = ZEMTDataProvider.provideLocalDatabase(context).breakDiscoverDao(),
        )
    }

    fun provideCollaboratorsRepository(context: Context): ZEMTCollaboratorsRepository {
        return ZEMTRoomCollaboratorsRepository(
            dao = ZEMTDataProvider.provideConversationsDatabase(context).collaboratorInChargeDao(),
        )
    }

}