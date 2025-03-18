package com.upax.zemytalents.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
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
import com.upax.zemytalents.ui.modules.confirm.home.ZEMTHomeConfirmViewModel
import com.upax.zemytalents.ui.modules.confirm.survey.ZEMTSurveyConfirmViewModel
import com.upax.zemytalents.ui.modules.discover.home.ZEMTHomeDiscoverViewModel
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverViewModel
import com.upax.zemytalents.ui.mytalents.ZEMTMyTalentsViewModel
import com.upax.zemytalents.ui.start.ZEMTStartViewModel
import com.upax.zemytalents.ui.talentmenu.ZEMTMenuTalentViewModel

@Suppress("UNCHECKED_CAST")
internal class ZEMTViewModelFactory(
    private val context: Context,
    private val moduleViewModel: ZEMTModuleViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (!modelClass.isAssignableFrom(moduleViewModel.clazz)) throw Exception()
        val appContext = context.applicationContext

        return when (moduleViewModel) {
            ZEMTModuleViewModel.START ->
                ZEMTViewModelProvider.provideStartViewModel(appContext)

            ZEMTModuleViewModel.ADVICE ->
                ZEMTViewModelProvider.provideAdviceViewModel(appContext)

            ZEMTModuleViewModel.HOME_DISCOVER ->
                ZEMTViewModelProvider.provideHomeDiscoverViewModel(appContext)

            ZEMTModuleViewModel.SURVEY_DISCOVER ->
                ZEMTViewModelProvider.provideSurveyDiscoverViewModel(appContext)

            ZEMTModuleViewModel.SURVEY_CONFIRM ->
                ZEMTViewModelProvider.provideSurveyConfirmViewModel(appContext)

            ZEMTModuleViewModel.SURVEY_APPLY ->
                ZEMTViewModelProvider.provideSurveyApplyViewModel(appContext)

            ZEMTModuleViewModel.HOME_CONFIRM ->
                ZEMTViewModelProvider.provideHomeConfirmModuleViewModel(appContext)

            ZEMTModuleViewModel.HOME_APPLY ->
                ZEMTViewModelProvider.provideHomeApplyModuleViewModel(appContext)

            ZEMTModuleViewModel.MENU_TALENT ->
                ZEMTViewModelProvider.provideMenuTalentViewModel(appContext)

            ZEMTModuleViewModel.TALENTS_RESUME ->
                ZEMTViewModelProvider.provideTalentsResumeViewModel(appContext)

            ZEMTModuleViewModel.CONVERSATION_TYPES ->
                ZEMTViewModelProvider.provideConversationTypesViewModel(appContext)

            ZEMTModuleViewModel.COLLABORATOR_LIST ->
                ZEMTViewModelProvider.provideCollaboratorListViewModel(appContext)

            ZEMTModuleViewModel.MAKE_CONVERSATION ->
                ZEMTViewModelProvider.provideMakeConversationViewModel(appContext)

            ZEMTModuleViewModel.COLLABORATOR_DETAIL ->
                ZEMTViewModelProvider.provideCollaboratorDetailViewModel(appContext)

            ZEMTModuleViewModel.FINAL_TALENTS ->
                ZEMTViewModelProvider.provideMyTalentsViewModel(appContext)

            ZEMTModuleViewModel.CONVERSATION_HISTORY ->
                ZEMTViewModelProvider.provideConversationViewModel(appContext)

            ZEMTModuleViewModel.COLLABORATOR_SEARCHER ->
                ZEMTViewModelProvider
                    .provideCollaboratorSearchViewModel(appContext, extras.createSavedStateHandle())

        } as T
    }
}

internal enum class ZEMTModuleViewModel(val clazz: Class<*>) {
    START(ZEMTStartViewModel::class.java),
    ADVICE(ZEMTAdviceViewModel::class.java),
    HOME_DISCOVER(ZEMTHomeDiscoverViewModel::class.java),
    SURVEY_DISCOVER(ZEMTSurveyDiscoverViewModel::class.java),
    SURVEY_CONFIRM(ZEMTSurveyConfirmViewModel::class.java),
    SURVEY_APPLY(ZEMTSurveyApplyViewModel::class.java),
    HOME_CONFIRM(ZEMTHomeConfirmViewModel::class.java),
    HOME_APPLY(ZEMTHomeApplyViewModel::class.java),
    MENU_TALENT(ZEMTMenuTalentViewModel::class.java),
    TALENTS_RESUME(ZEMTTalentsResumeViewModel::class.java),
    CONVERSATION_TYPES(ZEMTConversationTypesViewModel::class.java),
    COLLABORATOR_LIST(ZEMTCollaboratorListViewModel::class.java),
    MAKE_CONVERSATION(ZEMTMakeConversationViewModel::class.java),
    COLLABORATOR_DETAIL(ZEMTCollaboratorDetailViewModel::class.java),
    FINAL_TALENTS(ZEMTMyTalentsViewModel::class.java),
    CONVERSATION_HISTORY(ZEMTConversationViewModel::class.java),
    COLLABORATOR_SEARCHER(ZEMTCollaboratorSearcherViewModel::class.java)
}