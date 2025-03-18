package com.upax.zemytalents.ui.modules.apply.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch

internal class ZEMTSurveyApplyFragment : Fragment() {

    private val viewModel: ZEMTSurveyApplyViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.SURVEY_APPLY)
    }
    private val loader: ZCDSLoaderDialogFragment by lazy {
        ZCDSLoaderDialogFragment.Builder().build()
    }
    private val errorDialog: ZEMTErrorDialog by lazy {
        ZEMTErrorDialog(childFragmentManager, requireContext())
    }
    private lateinit var surveyDialogs: ZEMTSurveyApplyDialogs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? ZEMTHostActivity)?.updateAppBarTitle(title = getString(R.string.zemt_apply))
        if (needToStartViewModel(savedInstanceState)) viewModel.onStart()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                ZEMTSurveyApplyScreen(
                    uiState = uiState.value,
                    onAnswerStatusChange = { answer, status ->
                        viewModel.onAnswerStatusChange(answer, status)
                    }
                )
            }
        }
    }

    private fun needToStartViewModel(savedInstanceState: Bundle?): Boolean {
        val emptyUiState = ZEMTSurveyApplyUiState(
            dominantTalents = emptyList(), surveyTalents = emptyList()
        )
        return savedInstanceState == null || viewModel.uiState.value == emptyUiState
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFirstLaunch()
        surveyDialogs = ZEMTSurveyApplyDialogs(childFragmentManager, requireContext())
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            surveyDialogs.showAreYouSureToExit()
        }
        setDialogsListeners()
        collectStates()
        collectAlertsState()
    }

    private fun setDialogsListeners() {
        setSimpleDialogResultListener(ZEMTSurveyApplyDialogs.RequestKeys.EXIT_SURVEY) { _, onAccept ->
            if (onAccept) { findNavController().popBackStack() }
        }
        setSimpleDialogResultListener(ZEMTSurveyApplyDialogs.RequestKeys.NEXT_QUESTION) { _, _ ->
            viewModel.resetAlertsState()
            viewModel.onNextTalent()
        }
        setSimpleDialogResultListener(ZEMTSurveyApplyDialogs.RequestKeys.SURVEY_FINISHED) { _, _ ->
            viewModel.onSurveyFinished()
        }
        setSimpleDialogResultListener(ZEMTSurveyApplyDialogs.RequestKeys.SURVEY_IN_PROGRESS) { _, _ ->
            viewModel.resetAlertsState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR) { _, _ ->
            viewModel.resetAlertsState()
            viewModel.onSurveyFinished()
        }
        setSimpleDialogResultListener(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR) { _, _ ->
            viewModel.resetAlertsState()
            viewModel.onStart()
        }
    }

    private fun collectStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                showLoader(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.surveyFinishedState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it) navigateToMyTalentScreen()
            }
        }
    }

    private fun collectAlertsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.alertsState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                when (it) {
                    is ZEMTSurveyApplyAlerts.TalentApplied -> {
                        surveyDialogs.showTalentApplied(it.talent, it.remainingTalents)
                    }

                    ZEMTSurveyApplyAlerts.SurveyFinished -> {
                        surveyDialogs.showSurveyFinished()
                    }

                    ZEMTSurveyApplyAlerts.SurveyAnswerSyncError -> {
                        errorDialog.showErrorDialog(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR)
                    }

                    ZEMTSurveyApplyAlerts.SurveyDownloadedError -> {
                        errorDialog.showErrorDialog(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR)
                    }
                    ZEMTSurveyApplyAlerts.SurveyInProgress -> {
                        surveyDialogs.showSurveyInProgress()
                    }
                    null -> Unit
                }
            }
        }
    }

    private fun navigateToMyTalentScreen() {
        val jsonTalents = Gson().toJson(viewModel.uiState.value.dominantTalents)
        findNavController().navigate(
            ZEMTSurveyApplyFragmentDirections
                .actionZEMTSurveyApplyFragmentToZEMTPreviewMyTalentsFragment(jsonTalents)
        )
    }

    private fun showLoader(show: Boolean) {
        val isFragmentShowing = childFragmentManager.findFragmentByTag(ZCDSLoaderDialogFragment.TAG)
        if (show) {
            if (isFragmentShowing == null) loader.show(
                childFragmentManager,
                ZCDSLoaderDialogFragment.TAG
            )
            childFragmentManager.executePendingTransactions()
        } else {
            if (isFragmentShowing != null) loader.dismiss()
        }
    }

    companion object {
        private const val REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR =
            "REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR"
        private const val REQUEST_KEY_DOWNLOAD_SURVEY_ERROR = "REQUEST_KEY_DOWNLOAD_SURVEY_ERROR"
    }

}