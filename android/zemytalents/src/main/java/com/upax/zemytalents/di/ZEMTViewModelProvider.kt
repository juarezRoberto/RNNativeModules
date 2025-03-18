package com.upax.zemytalents.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.upax.zemytalents.ui.advice.ZEMTAdviceViewModel
import com.upax.zemytalents.ui.conversations.collaboratordetail.ZEMTCollaboratorDetailViewModel
import com.upax.zemytalents.ui.conversations.collaboratorlist.ZEMTCollaboratorListViewModel
import com.upax.zemytalents.ui.conversations.collaboratorsearcher.ZEMTCollaboratorSearcherViewModel
import com.upax.zemytalents.ui.conversations.conversationhistory.ZEMTConversationViewModel
import com.upax.zemytalents.ui.conversations.conversationtypes.ZEMTConversationTypesViewModel
import com.upax.zemytalents.ui.conversations.makeconversation.ZEMTMakeConversationViewModel
import com.upax.zemytalents.ui.conversations.talentsresume.ZEMTTalentsResumeViewModel
import com.upax.zemytalents.ui.modules.apply.home.ZEMTHomeApplyViewModel
import com.upax.zemytalents.ui.modules.apply.survey.ZEMTSurveyApplyViewModel
import com.upax.zemytalents.ui.modules.apply.survey.mapper.ZEMTTalentsApplyToUiModelMapper
import com.upax.zemytalents.ui.modules.confirm.home.ZEMTHomeConfirmViewModel
import com.upax.zemytalents.ui.modules.confirm.survey.ZEMTSurveyConfirmViewModel
import com.upax.zemytalents.ui.modules.confirm.survey.mapper.ZEMTTalentsConfirmToUiModelMapper
import com.upax.zemytalents.ui.modules.discover.home.ZEMTHomeDiscoverViewModel
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverViewModel
import com.upax.zemytalents.ui.modules.discover.survey.mapper.ZEMTDiscoverGroupQuestionsToUiModelMapper
import com.upax.zemytalents.ui.mytalents.ZEMTMyTalentsViewModel
import com.upax.zemytalents.ui.start.ZEMTStartViewModel
import com.upax.zemytalents.ui.talentmenu.ZEMTMenuTalentViewModel

internal object ZEMTViewModelProvider {

    fun provideStartViewModel(context: Context): ZEMTStartViewModel {
        return ZEMTStartViewModel(
            ZEMTRepositoryProvider.provideModulesRepository(context),
            ZEMTDataProvider.provideLocalPreferences(context),
            ZEMTRepositoryProvider.provideCaptionsDownloader(context)
        )
    }

    fun provideAdviceViewModel(context: Context): ZEMTAdviceViewModel {
        return ZEMTAdviceViewModel(
            ZEMTDataProvider.provideLocalPreferences(context)
        )
    }

    fun provideHomeDiscoverViewModel(context: Context): ZEMTHomeDiscoverViewModel {
        return ZEMTHomeDiscoverViewModel(
            getUserDataUseCase = ZEMTUseCaseProvider.provideGetUserDataUseCase(context),
            getProgressUseCase = ZEMTUseCaseProvider.provideGetSurveyProgressUseCase(context),
            moduleRepository = ZEMTRepositoryProvider.provideModulesRepository(context)
        )
    }

    fun provideSurveyDiscoverViewModel(context: Context): ZEMTSurveyDiscoverViewModel {
        return ZEMTSurveyDiscoverViewModel(
            groupQuestionsRepository = ZEMTRepositoryProvider
                .provideGroupQuestionsDiscoverRepository(context),
            saveAnswerUseCase = ZEMTUseCaseProvider.provideSaveAnswerDiscoverUseCase(context),
            surveyDownloader = ZEMTDataProvider.provideSurveyDiscoverDownloader(context),
            skipGroupQuestionsUseCase = ZEMTUseCaseProvider
                .provideSkipGroupQuestionsUseCase(context),
            discoverGroupQuestionToUiModelMapper = ZEMTDiscoverGroupQuestionsToUiModelMapper(),
            synchronizeAnswersUseCase = ZEMTUseCaseProvider.provideSynchronizeAnswersDiscoverUseCase(
                context
            ),
            surveyReminder = ZEMTRepositoryProvider.provideSurveyDiscoverReminder(context),
            breaksRepository = ZEMTRepositoryProvider.provideSurveyDiscoverBreaksRepository(context),
            locationUpdater = ZEMTDataProvider.provideLocationUpdater(context)
        )
    }

    fun provideSurveyConfirmViewModel(context: Context): ZEMTSurveyConfirmViewModel {
        return ZEMTSurveyConfirmViewModel(
            getSurveyConfirmUseCase = ZEMTUseCaseProvider.provideGetSurveyConfirmUseCase(context),
            talentsConfirmMapper = ZEMTTalentsConfirmToUiModelMapper(),
            synchronizeAnswersUseCase = ZEMTUseCaseProvider
                .provideSynchronizeAnswersConfirmUseCase(context),
            locationUpdater = ZEMTDataProvider.provideLocationUpdater(context),
            dbSurveyConfirmRepository = ZEMTRepositoryProvider
                .provideSurveyConfirmRepository(context),
            saveConfirmSurveyAnswerUseCase = ZEMTUseCaseProvider
                .provideSaveConfirmSurveyAnswerUseCase(context)
        )
    }

    fun provideSurveyApplyViewModel(context: Context): ZEMTSurveyApplyViewModel {
        return ZEMTSurveyApplyViewModel(
            getSurveyApplyUseCase = ZEMTUseCaseProvider.provideGetSurveyApplyUseCase(context),
            getUserTalentsUseCase = ZEMTUseCaseProvider.provideGetMyTalentsUseCase(context),
            surveyTalentsToUiModelMapper = ZEMTTalentsApplyToUiModelMapper(),
            synchronizeAnswersUseCase = ZEMTUseCaseProvider.provideSynchronizeApplyAnswersUseCase(
                context
            ),
            saveSurveyApplyUseCase = ZEMTUseCaseProvider.provideSurveyApplyAnswerUseCase(context),
            locationUpdater = ZEMTDataProvider.provideLocationUpdater(context)
        )
    }

    fun provideHomeConfirmModuleViewModel(context: Context): ZEMTHomeConfirmViewModel {
        return ZEMTHomeConfirmViewModel(
            getUserDataUseCase = ZEMTUseCaseProvider.provideGetUserDataUseCase(context),
            moduleRepository = ZEMTRepositoryProvider.provideModulesRepository(context),
            talentsRepository = ZEMTRepositoryProvider.provideTalentsRepositoryImpl(context, true),
            dbSurveyConfirmRepository = ZEMTRepositoryProvider.provideSurveyConfirmRepository(
                context
            )
        )
    }

    fun provideHomeApplyModuleViewModel(context: Context): ZEMTHomeApplyViewModel {
        return ZEMTHomeApplyViewModel(
            ZEMTRepositoryProvider.provideModulesRepository(context),
            ZEMTUseCaseProvider.provideGetSurveyApplyProgressUseCase(context),
        )
    }

    fun provideMenuTalentViewModel(context: Context): ZEMTMenuTalentViewModel {
        return ZEMTMenuTalentViewModel(
            getUser = ZEMTUseCaseProvider.provideGetUserDataUseCase(context),
            getCollaboratorsInCharge = ZEMTUseCaseProvider.provideGetCollaboratorsInChargeUseCase(
                context
            ),
            captionsDownloader = ZEMTRepositoryProvider.provideCaptionsDownloader(context),
            getTalentsMenu = ZEMTUseCaseProvider.provideGetTalentsMenuUseCase(context),
            localPreferences = ZEMTDataProvider.provideLocalPreferences(context),
            getTalentsCompletedById = ZEMTUseCaseProvider.provideGetTalentsCompletedById(context),
            getCaptionTextUseCase = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideTalentsResumeViewModel(context: Context): ZEMTTalentsResumeViewModel {
        return ZEMTTalentsResumeViewModel(
            getTalents = ZEMTUseCaseProvider.provideGetUserTalentsUseCase(context),
            getConversationsUseCase = ZEMTUseCaseProvider.provideGetConversationListUseCase(context),
            getTalentsCompletedByIdUseCase = ZEMTUseCaseProvider.provideGetTalentsCompletedById(
                context
            ),
            getUserDataUseCase = ZEMTUseCaseProvider.provideGetUserDataUseCase(context),
            getCaptions = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideConversationTypesViewModel(context: Context): ZEMTConversationTypesViewModel {
        return ZEMTConversationTypesViewModel(
            getConversationList = ZEMTUseCaseProvider.provideGetConversationListUseCase(context),
            getCaptions = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideCollaboratorListViewModel(context: Context): ZEMTCollaboratorListViewModel {
        return ZEMTCollaboratorListViewModel(
            collaboratorUseCase = ZEMTUseCaseProvider.provideGetCollaboratorsInChargeWithCompletedStatusUseCase(
                context
            ),
            progressUseCase = ZEMTUseCaseProvider.provideProgressUseCase(context),
            getCaptions = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideMakeConversationViewModel(context: Context): ZEMTMakeConversationViewModel {
        return ZEMTMakeConversationViewModel(
            getConversationUseCase = ZEMTUseCaseProvider.provideGetConversationListUseCase(context),
            getPhrasesUseCase = ZEMTUseCaseProvider.provideGetPhrasesUseCase(context),
            progressUseCase = ZEMTUseCaseProvider.provideProgressUseCase(context),
            saveConversationUseCase = ZEMTUseCaseProvider.provideSaveConversationUseCase(context),
            getCaptions = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideCollaboratorDetailViewModel(context: Context): ZEMTCollaboratorDetailViewModel {
        return ZEMTCollaboratorDetailViewModel(
            getConversationList = ZEMTUseCaseProvider.provideGetConversationListUseCase(context),
            getUserDataUseCase = ZEMTUseCaseProvider.provideGetUserDataUseCase(context)
        )
    }

    fun provideMyTalentsViewModel(context: Context): ZEMTMyTalentsViewModel {
        return ZEMTMyTalentsViewModel(
            ZEMTUseCaseProvider.provideGetUserDataUseCase(context),
            ZEMTRepositoryProvider.provideTalentsRepositoryWithCache(context),
            ZEMTRepositoryProvider.provideModulesRepository(context)
        )
    }

    fun provideConversationViewModel(context: Context): ZEMTConversationViewModel {
        return ZEMTConversationViewModel(
            getConversationsHistory = ZEMTUseCaseProvider.provideGetConversationsHistoryUseCase(
                context
            ),
            getLocalTexts = ZEMTUseCaseProvider.provideGetCaptionTextUseCase(context)
        )
    }

    fun provideCollaboratorSearchViewModel(
        context: Context,
        savedStateHandle: SavedStateHandle
    ): ZEMTCollaboratorSearcherViewModel {
        return ZEMTCollaboratorSearcherViewModel(
            collaboratorsRepository = ZEMTRepositoryProvider.provideCollaboratorsRepository(context),
            progressUseCase = ZEMTUseCaseProvider.provideProgressUseCase(context),
            savedStateHandle = savedStateHandle
        )
    }
}