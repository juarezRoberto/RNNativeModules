package com.upax.zemytalents.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.prometeo.keymanager.expose.ZKMPreferencesUtils
import com.prometeo.keymanager.preferences.ZKMPreferences
import com.upax.zcsessioninfo.domain.repository.ZCSISessionInfo
import com.upax.zcsessioninfo.expose.ZCSIExpose
import com.upax.zemytalents.common.ZEMTEnvironment
import com.upax.zemytalents.data.ZEMTLocationUpdaterImpl
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabase
import com.upax.zemytalents.data.local.database.ZEMTTalentsDatabaseBuilder
import com.upax.zemytalents.data.local.database.conversations.ZEMTConversationsDatabase
import com.upax.zemytalents.data.local.database.conversations.ZEMTConversationsDatabaseBuilder
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerDiscoverToEntityMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTQuestionDiscoverToEntityMapper
import com.upax.zemytalents.data.local.preferences.ZEMTLocalPreferences
import com.upax.zemytalents.data.remote.api.ZEMTCaptionsApi
import com.upax.zemytalents.data.remote.api.ZEMTConversationsApi
import com.upax.zemytalents.data.remote.api.ZEMTMyTalentsApi
import com.upax.zemytalents.data.remote.api.ZEMTSurveyDiscoverApiService
import com.upax.zemytalents.data.remote.mapper.ZEMTAnswerOptionResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTAttachmentDiscoverResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTBreakDiscoverResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTGroupQuestionsDiscoverResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTQuestionDiscoverResponseToModelMapper
import com.upax.zemytalents.data.remote.mapper.ZEMTSurveyDiscoverResponseToModelMapper
import com.upax.zemytalents.data.repository.ZEMTSurveyDiscoverDownloaderImpl
import com.upax.zemytalents.data.repository.ZEMTSurveyDiscoverRegistrar
import com.upax.zemytalents.data.repository.test.ZEMTDummyLocationUpdater
import com.upax.zemytalents.data.repository.test.ZEMTTestDatabase
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverDownloader
import com.upax.zemytalents.domain.usecases.ZEMTLocationUpdater

internal object ZEMTDataProvider {

    @VisibleForTesting
    var isTestingEnvironment = false

    fun provideLocalDatabase(appContext: Context): ZEMTTalentsDatabase {
        if (isTestingEnvironment) {
            return ZEMTTestDatabase.getInstance(appContext)
        }
        return ZEMTTalentsDatabaseBuilder.getInstance(appContext)
    }

    fun provideConversationsDatabase(appContext: Context): ZEMTConversationsDatabase {
        return ZEMTConversationsDatabaseBuilder.getInstance(appContext)
    }

    fun provideLocalPreferences(context: Context): ZEMTLocalPreferences {
        return ZEMTLocalPreferences.getInstance(
            provideKeyManagerPreferences(context)
        )
    }

    fun provideKeyManagerPreferences(context: Context): ZKMPreferences {
        return ZKMPreferencesUtils.buildSecureSharedPreferences(
            context, ZEMTLocalPreferences.FILE_NAME
        )
    }

    fun provideSessionInfo(context: Context): ZCSISessionInfo {
        return ZCSIExpose.getSessionInfo(context)
    }

    fun provideMyTalentsApi(context: Context): ZEMTMyTalentsApi {
        if (isTestingEnvironment) {
            return ZEMTApiServiceProvider.buildTestApiService(ZEMTMyTalentsApi::class.java)
        }
        return ZEMTApiServiceProvider.provideApiServiceV2(
            context,
            ZEMTMyTalentsApi::class.java,
            ZEMTEnvironment.getCustomTalentsUrl()
        )
    }

    fun provideDiscoverModuleApiService(
        context: Context,
    ): ZEMTSurveyDiscoverApiService {
        if (isTestingEnvironment) {
            return ZEMTApiServiceProvider.buildTestApiService(ZEMTSurveyDiscoverApiService::class.java)
        }
        return ZEMTApiServiceProvider.provideApiServiceV2(
            context,
            ZEMTSurveyDiscoverApiService::class.java,
            ZEMTEnvironment.getCustomTalentsUrl()
        )
    }

    fun provideSurveyDiscoverDownloader(context: Context): ZEMTSurveyDiscoverDownloader {
        return ZEMTSurveyDiscoverDownloaderImpl(
            api = provideDiscoverModuleApiService(context),
            discoverSurveyResponseToModelMapper = ZEMTSurveyDiscoverResponseToModelMapper(
                breakResponseToModelMapper = ZEMTBreakDiscoverResponseToModelMapper(
                    attachmentResponseToModelMapper = ZEMTAttachmentDiscoverResponseToModelMapper()
                ),
                groupQuestionsResponseToModelMapper = ZEMTGroupQuestionsDiscoverResponseToModelMapper(
                    questionResponseToModelMapper = ZEMTQuestionDiscoverResponseToModelMapper(
                        answerOptionResponseToModelMapper = ZEMTAnswerOptionResponseToModelMapper()
                    )
                )
            ),
            surveyRegistrar = provideSurveyDiscoverRegistrar(context),
            surveyDao = provideLocalDatabase(context).surveyDiscoverDao(),
            moduleDao = provideLocalDatabase(context).moduleDao(),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context)
        )
    }

    private fun provideSurveyDiscoverRegistrar(context: Context): ZEMTSurveyDiscoverRegistrar {
        val database = provideLocalDatabase(context)
        return ZEMTSurveyDiscoverRegistrar(
            surveyDao = database.surveyDiscoverDao(),
            questionDao = database.questionDiscoverDao(),
            answerOptionDao = database.answerOptionDiscoverDao(),
            breaksDao = database.breakDiscoverDao(),
            questionToEntityMapper = ZEMTQuestionDiscoverToEntityMapper(),
            answerToEntityMapper = ZEMTAnswerDiscoverToEntityMapper()
        )
    }

    fun provideConversationsApi(
        context: Context,
        useAlternativeUrl: Boolean = false,
    ): ZEMTConversationsApi {
        return ZEMTApiServiceProvider.provideApiServiceV2(
            context,
            ZEMTConversationsApi::class.java,
            ZEMTEnvironment.getCustomTalentsUrl(useAlternativeUrl)
        )
    }

    fun provideLocationUpdater(context: Context): ZEMTLocationUpdater {
        if (isTestingEnvironment) {
            return ZEMTDummyLocationUpdater()
        }
        return ZEMTLocationUpdaterImpl(
            context,
            locationRepository = ZEMTRepositoryProvider.provideLocationRepository(context)
        )
    }

    fun provideCaptionsApi(context: Context): ZEMTCaptionsApi {
        return ZEMTApiServiceProvider.provideApiServiceV2(
            context,
            ZEMTCaptionsApi::class.java,
            ZEMTEnvironment.getCustomTalentsUrl()
        )
    }
}