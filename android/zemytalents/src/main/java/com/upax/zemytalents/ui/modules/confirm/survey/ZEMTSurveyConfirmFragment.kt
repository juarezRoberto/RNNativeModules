package com.upax.zemytalents.ui.modules.confirm.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.modules.confirm.survey.models.ZEMTSurveyConfirmServiceState
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch

internal class ZEMTSurveyConfirmFragment : Fragment() {

    private val args: ZEMTSurveyConfirmFragmentArgs by navArgs()
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(childFragmentManager, requireContext())
    }
    private val viewModel: ZEMTSurveyConfirmViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.SURVEY_CONFIRM)
    }
    private val dialogs: ZEMTSurveyConfirmDialogs by lazy {
        ZEMTSurveyConfirmDialogs(childFragmentManager, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as ZEMTHostActivity).updateAppBarTitle(title = getString(R.string.zemt_confirm))
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val serviceState by viewModel.serviceState.collectAsStateWithLifecycle()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                if (serviceState is ZEMTSurveyConfirmServiceState.Success) {
                    ZEMTSurveyConfirmScreen(
                        uiState = mergeTalentAnimations(uiState),
                        onSaveQuestionAnswer = { id, options ->
                            viewModel.saveQuestionAnswer(id, options)
                        },
                        onHideIntroduction = { viewModel.hideIntroduction() }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFirstLaunch()
        setListeners()
        collectUiState()
        viewModel.getSurvey()
    }

    private fun setListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            dialogs.showAreYouSureToExit()
        }
        setSimpleDialogResultListener(ZEMTSurveyConfirmDialogs.RequestKeys.EXIT_SURVEY) { _, onYes ->
            if (onYes) {
                findNavController().popBackStack()
            }
        }
        setSimpleDialogResultListener(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR) { _, _ ->
            findNavController().navigateUp()
        }
        setSimpleDialogResultListener(ZEMTSurveyConfirmDialogs.RequestKeys.CONFIRM_SURVEY_END) { _, onContinue ->
            if (onContinue) {
                viewModel.onSurveyFinished()
            } else {
                viewModel.clearCurrentTalentAnswers()
            }
        }
        setSimpleDialogResultListener(ZEMTSurveyConfirmDialogs.RequestKeys.NEXT_QUESTION) { _, onContinue ->
            if (onContinue) {
                viewModel.moveToNextTalent()
            } else { // cancel option
                viewModel.clearCurrentTalentAnswers()
            }
        }
        setSimpleDialogResultListener(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR) { _, _ ->
            viewModel.onRetrySurveyFinished()
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(ZEMTSurveyConfirmDialogs.RequestKeys.SAVED_PROGRESS) { _, _ ->
            viewModel.resetShowSavedProgressDialogState()
        }
    }

    /**
     * Se insertan las url de los lotties en cada talento de confirma, estos vienen de la pantalla
     * anterior, las regresa el servicio de summary
     */
    private fun mergeTalentAnimations(originalUiState: ZEMTSurveyConfirmUiState): ZEMTSurveyConfirmUiState {
        val homeTalents = Gson().fromJson(
            args.homeTalents,
            Array<ZEMTTalent>::class.java
        ).toList()

        val updatedTalents = originalUiState.talents.map { talent ->
            if (homeTalents.any { homeTalent -> homeTalent.id == talent.id }) {
                val homeTalent = homeTalents.find { it.id == talent.id }
                val lottieUrl =
                    homeTalent?.attachment?.firstOrNull { it.type == ZEMTAttachmentType.LOTTIE }?.url.orEmpty()
                talent.copy(lottieUrl = lottieUrl)
            } else talent
        }

        return originalUiState.copy(talents = updatedTalents)
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.serviceState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    ZEMTSurveyConfirmServiceState.Loading -> showLoader()

                    ZEMTSurveyConfirmServiceState.Error -> {
                        hideLoader()
                        errorDialog.showErrorDialog(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR)
                    }

                    is ZEMTSurveyConfirmServiceState.Success -> hideLoader()
                    else -> Unit
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it.showFinishedSurveyDialog) {
                    dialogs.showSurveyEnd()
                }
                if (it.showRemainingQuestionsDialog != null) {
                    dialogs.showNextTalent(it.showRemainingQuestionsDialog.remaining)
                }
                if (it.showSavedProgressDialog) {
                    dialogs.showSavedProgress()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it) showLoader() else hideLoader()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.alertsState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                it?.let(::handleAlertsState)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.surveyFinishedState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it) navigateToHomeApplyScreen()
            }
        }
    }

    private fun showLoader() {
        if (!loader.isAdded) loader.show(childFragmentManager, ZCDSLoaderDialogFragment.TAG)
    }

    private fun hideLoader() {
        if (loader.isAdded) loader.dismiss()
    }

    private fun handleAlertsState(alert: ZEMTSurveyConfirmAlerts) {
        when (alert) {
            ZEMTSurveyConfirmAlerts.SURVEY_ANSWER_SYNCHRONIZER_ERROR -> {
                errorDialog.showErrorDialog(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR)
            }
        }
    }

    private fun navigateToHomeApplyScreen() {
        findNavController().navigate(
            ZEMTSurveyConfirmFragmentDirections
                .actionZEMTConfirmSurveyFragmentToZEMTHomeApplyFragment()
        )
    }

    companion object {
        internal const val REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR =
            "REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR"
        internal const val REQUEST_KEY_DOWNLOAD_SURVEY_ERROR = "REQUEST_KEY_DOWNLOAD_SURVEY_ERROR"
    }

}