package com.upax.zemytalents.di

import android.content.Context
import com.upax.zemytalents.domain.usecases.ZEMTClearDatabaseUseCase
import com.upax.zemytalents.domain.usecases.ZEMTClearLocalDataUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetCaptionTextUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetMyUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyProgressUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyApplyUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyConfirmUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetSurveyDiscoverProgressUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdListUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsMenuUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserDataUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSaveAnswerDiscoverUserCase
import com.upax.zemytalents.domain.usecases.ZEMTSaveSurveyApplyAnswerUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSkipGroupQuestionsUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersConfirmUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeAnswersDiscoverUseCase
import com.upax.zemytalents.domain.usecases.ZEMTSynchronizeApplyAnswersUseCase
import com.upax.zemytalents.domain.usecases.ZEMTUpdateGroupQuestionsIndexDiscoverUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTConversationProgressUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetCollaboratorsInChargeUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationsHistoryUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetPhrasesUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTSaveConversationUseCase
import com.upax.zemytalents.domain.usecases.modules.confirm.ZEMTSaveConfirmSurveyAnswerUseCase

internal object ZEMTUseCaseProvider {

    private fun provideClearDatabaseUseCase(context: Context): ZEMTClearDatabaseUseCase {
        return ZEMTClearDatabaseUseCase(
            database = ZEMTDataProvider.provideLocalDatabase(context),
            conversationsDatabase = ZEMTDataProvider.provideConversationsDatabase(context)
        )
    }

    fun provideClearLocalDataUseCase(context: Context): ZEMTClearLocalDataUseCase {
        return ZEMTClearLocalDataUseCase(
            provideClearDatabaseUseCase(context),
            ZEMTDataProvider.provideLocalPreferences(context)
        )
    }

    fun provideGetUserDataUseCase(context: Context): ZEMTGetUserDataUseCase {
        return ZEMTGetUserDataUseCase(
            ZEMTRepositoryProvider.provideUserRepository(context)
        )
    }

    fun provideSaveAnswerDiscoverUseCase(context: Context): ZEMTSaveAnswerDiscoverUserCase {
        return ZEMTSaveAnswerDiscoverUserCase(
            answerOptionRepository = ZEMTRepositoryProvider.provideAnswerOptionDiscoverRepository(
                context
            ),
            answerSavedRepository = ZEMTRepositoryProvider.provideAnswerSavedDiscoverRepository(
                context
            ),
            updateGroupQuestionIndexUseCase = provideUpdateGroupQuestionsIndexUseCase(context)
        )
    }

    fun provideSynchronizeAnswersDiscoverUseCase(context: Context): ZEMTSynchronizeAnswersDiscoverUseCase {
        return ZEMTSynchronizeAnswersDiscoverUseCase(
            answerSavedRepository = ZEMTRepositoryProvider.provideAnswerSavedDiscoverRepository(
                context
            ),
            answerSynchronizer = ZEMTRepositoryProvider.provideAnswersSynchronizer(
                context
            ),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            surveyDiscoverRepository = ZEMTRepositoryProvider.provideSurveyDiscoverRepository(
                context
            )
        )
    }

    fun provideUpdateGroupQuestionsIndexUseCase(context: Context): ZEMTUpdateGroupQuestionsIndexDiscoverUseCase {
        return ZEMTUpdateGroupQuestionsIndexDiscoverUseCase(
            ZEMTRepositoryProvider.provideGroupQuestionsDiscoverRepository(context)
        )
    }

    fun provideSkipGroupQuestionsUseCase(context: Context): ZEMTSkipGroupQuestionsUseCase {
        return ZEMTSkipGroupQuestionsUseCase(
            provideUpdateGroupQuestionsIndexUseCase(context),
            provideSaveAnswerDiscoverUseCase(context),
            ZEMTRepositoryProvider.provideGroupQuestionsDiscoverRepository(context)
        )
    }

    fun provideGetSurveyProgressUseCase(context: Context): ZEMTGetSurveyDiscoverProgressUseCase {
        return ZEMTGetSurveyDiscoverProgressUseCase(
            ZEMTRepositoryProvider.provideGroupQuestionsDiscoverRepository(context = context)
        )
    }

    fun provideGetCollaboratorsInChargeUseCase(context: Context): ZEMTGetCollaboratorsInChargeUseCase {
        return ZEMTGetCollaboratorsInChargeUseCase(
            repo = ZEMTRepositoryProvider.provideConversationsRepository(
                context = context,
                useAlternativeUrl = true
            ),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context = context)
        )
    }

    fun provideGetCollaboratorsInChargeWithCompletedStatusUseCase(context: Context): ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase {
        return ZEMTGetCollaboratorsInChargeWithCompletedStatusUseCase(
            repo = ZEMTRepositoryProvider.provideConversationsRepository(
                context = context,
                useAlternativeUrl = true
            ),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context = context),
            getTalentsCompletedByIdList = provideGetTalentsCompletedByIdList(context),
            collaboratorInChargeDao = ZEMTDataProvider.provideConversationsDatabase(context)
                .collaboratorInChargeDao()
        )
    }

    fun provideGetTalentsMenuUseCase(context: Context): ZEMTGetTalentsMenuUseCase =
        ZEMTGetTalentsMenuUseCase(context)

    fun provideGetUserTalentsUseCase(context: Context): ZEMTGetUserTalentsUseCase {
        return ZEMTGetUserTalentsUseCase(
            ZEMTRepositoryProvider.provideTalentsRepositoryImpl(context, true)
        )
    }

    fun provideGetConversationListUseCase(context: Context): ZEMTGetConversationListUseCase {
        return ZEMTGetConversationListUseCase(
            conversationRepository = ZEMTRepositoryProvider.provideConversationsRepository(
                context
            ),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context)
        )
    }

    fun provideSynchronizeAnswersConfirmUseCase(context: Context): ZEMTSynchronizeAnswersConfirmUseCase {
        return ZEMTSynchronizeAnswersConfirmUseCase(
            answerSynchronizer = ZEMTRepositoryProvider.provideAnswersSynchronizer(context),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            surveyConfirmRepository = ZEMTRepositoryProvider.provideSurveyConfirmRepository(context),
            moduleDao = ZEMTDataProvider.provideLocalDatabase(context).moduleDao()
        )
    }

    fun provideSynchronizeApplyAnswersUseCase(context: Context): ZEMTSynchronizeApplyAnswersUseCase {
        return ZEMTSynchronizeApplyAnswersUseCase(
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            surveyApplyRepository = ZEMTRepositoryProvider.provideSurveyApplyRepository(context),
            answerSynchronizer = ZEMTRepositoryProvider.provideAnswersSynchronizer(context),
            surveyApplyAnswersRepository = ZEMTRepositoryProvider.provideSurveyApplyAnswersRepository(
                context
            ),
            talentsCompletedRepository = ZEMTRepositoryProvider
                .provideTalentsCompletedRepository(context)
        )
    }

    fun provideGetSurveyApplyUseCase(context: Context): ZEMTGetSurveyApplyUseCase {
        return ZEMTGetSurveyApplyUseCase(
            surveyApplyRepository = ZEMTRepositoryProvider.provideSurveyApplyRepository(context),
            talentsRepository = ZEMTRepositoryProvider.provideTalentsApplyRepository(context),
            surveyApplyAnswersRepository = ZEMTRepositoryProvider.provideSurveyApplyAnswersRepository(
                context
            )
        )
    }

    fun provideGetMyTalentsUseCase(context: Context): ZEMTGetMyUserTalentsUseCase {
        return ZEMTGetMyUserTalentsUseCase(
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            surveyTalentsRepository = ZEMTRepositoryProvider
                .provideTalentsRepositoryWithCache(context)
        )
    }

    fun provideGetSurveyConfirmUseCase(context: Context): ZEMTGetSurveyConfirmUseCase {
        return ZEMTGetSurveyConfirmUseCase(
            surveyConfirmRepository = ZEMTRepositoryProvider.provideSurveyConfirmRepository(context),
            talentsRepository = ZEMTRepositoryProvider.provideTalentsRepository(context)
        )
    }

    fun provideGetPhrasesUseCase(context: Context): ZEMTGetPhrasesUseCase {
        return ZEMTGetPhrasesUseCase(
            repo = ZEMTRepositoryProvider.provideConversationsRepository(context),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context)
        )
    }

    fun provideProgressUseCase(context: Context): ZEMTConversationProgressUseCase {
        return ZEMTConversationProgressUseCase(
            repo = ZEMTRepositoryProvider.provideConversationsRepository(context),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context)
        )
    }

    fun provideGetTalentsCompletedById(context: Context): ZEMTGetTalentsCompletedByIdUseCase {
        return ZEMTGetTalentsCompletedByIdUseCase(
            repo = ZEMTRepositoryProvider.provideTalentsRepositoryImpl(context, false),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            talentsCompletedDao = ZEMTDataProvider.provideLocalDatabase(context)
                .talentsCompletedDao()
        )
    }

    fun provideGetTalentsCompletedByIdList(context: Context): ZEMTGetTalentsCompletedByIdListUseCase {
        return ZEMTGetTalentsCompletedByIdListUseCase(
            repo = ZEMTRepositoryProvider.provideTalentsRepositoryImpl(context, false),
            talentsCompletedDao = ZEMTDataProvider.provideLocalDatabase(context)
                .talentsCompletedDao()
        )
    }

    fun provideSaveConversationUseCase(context: Context): ZEMTSaveConversationUseCase {
        return ZEMTSaveConversationUseCase(
            repository = ZEMTRepositoryProvider.provideConversationsRepository(context),
            userRepository = ZEMTRepositoryProvider.provideUserRepository(context),
            deviceRepository = ZEMTRepositoryProvider.provideDeviceRepository()
        )
    }

    fun provideGetConversationsHistoryUseCase(context: Context): ZEMTGetConversationsHistoryUseCase {
        return ZEMTGetConversationsHistoryUseCase(
            repo = ZEMTRepositoryProvider.provideConversationsRepository(context)
        )
    }

    fun provideGetCaptionTextUseCase(context: Context): ZEMTGetCaptionTextUseCase {
        return ZEMTGetCaptionTextUseCase(
            ZEMTRepositoryProvider.provideCaptionsFinder(context)
        )
    }

    fun provideSaveConfirmSurveyAnswerUseCase(context: Context): ZEMTSaveConfirmSurveyAnswerUseCase {
        return ZEMTSaveConfirmSurveyAnswerUseCase(
            ZEMTRepositoryProvider.provideSurveyConfirmRepository(context),
            ZEMTRepositoryProvider.provideLocationRepository(context),
            ZEMTRepositoryProvider.provideDateRepository(),
        )

    }

    fun provideGetSurveyApplyProgressUseCase(context: Context): ZEMTGetSurveyApplyProgressUseCase {
        return ZEMTGetSurveyApplyProgressUseCase(
            answersRepository = ZEMTRepositoryProvider.provideSurveyApplyAnswersRepository(context)
        )
    }

    fun provideSurveyApplyAnswerUseCase(context: Context): ZEMTSaveSurveyApplyAnswerUseCase {
        return ZEMTSaveSurveyApplyAnswerUseCase(
            surveyAnswersRepository = ZEMTRepositoryProvider.provideSurveyApplyAnswersRepository(
                context
            ),
            locationRepository = ZEMTRepositoryProvider.provideLocationRepository(context),
            dateRepository = ZEMTRepositoryProvider.provideDateRepository()
        )
    }

}