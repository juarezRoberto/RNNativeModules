package com.upax.zemytalents.ui.modules.discover.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.upax.zcdesignsystem.dialog.ZCDSLoaderDialogFragment
import com.upax.zcdesignsystem.utils.setSimpleDialogResultListener
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.di.ZEMTModuleViewModel
import com.upax.zemytalents.di.ZEMTViewModelFactory
import com.upax.zemytalents.ui.ZEMTHostActivity
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverDialogs.Companion.REQUEST_KEY_TAKE_BREAK
import com.upax.zemytalents.ui.modules.discover.survey.screen.ZEMTSurveyDiscoverScreen
import com.upax.zemytalents.ui.shared.ZEMTErrorDialog
import kotlinx.coroutines.launch

internal class ZEMTSurveyDiscoverFragment : Fragment() {

    private val viewModel: ZEMTSurveyDiscoverViewModel by viewModels {
        ZEMTViewModelFactory(requireContext(), ZEMTModuleViewModel.SURVEY_DISCOVER)
    }
    private lateinit var loader: ZCDSLoaderDialogFragment
    private lateinit var errorDialog: ZEMTErrorDialog
    private lateinit var discoverDialogs: ZEMTSurveyDiscoverDialogs
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            discoverDialogs.showAreYouSureWantToLeave()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(uiState.value.userHasFinishSurvey) {
                    if (uiState.value.userHasFinishSurvey) viewModel.onSurveyFinished()
                }

                ZEMTSurveyDiscoverScreen(
                    uiState = uiState.value,
                    onAnswerSelected = viewModel::onSaveAnswer,
                    onGroupQuestionsSkip = viewModel::onGroupQuestionsSkip
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFirstLaunch()
        loader = ZCDSLoaderDialogFragment.Builder().build()
        errorDialog = ZEMTErrorDialog(childFragmentManager, requireContext())
        discoverDialogs = ZEMTSurveyDiscoverDialogs(childFragmentManager, requireContext())
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)
        registerDialogListeners()
        updateTopAppBarTitle()
        collectUiState()
    }

    private fun registerDialogListeners() {
        setSimpleDialogResultListener(REQUEST_KEY_ARE_YOU_SURE_WANT_TO_LEAVE) { _, accepted ->
            if (accepted) {
                findNavController().popBackStack()
            }
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR) { _, _ ->
            viewModel.onRetrySurveyDownload()
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR) { _, _ ->
            viewModel.onRetrySurveyFinished()
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_MAX_NUMBER_OF_NEUTRAL_ANSWERS) { _, _ ->
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_SAVE_POINT) { _, _ ->
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_FIRST_TIME) { _, _ ->
            viewModel.onRestartAlertState()
        }
        setSimpleDialogResultListener(REQUEST_KEY_TAKE_BREAK) { _, accepted ->
            viewModel.onRestartAlertState()
            if (accepted) findNavController().popBackStack()
        }
    }

    private fun updateTopAppBarTitle() {
        (requireActivity() as? ZEMTHostActivity)?.updateAppBarTitle(
            title = getString(R.string.zemt_my_talents),
            subtitle = getString(R.string.zemt_discover),
            style = ZCDSTopAppBar.TitlesStyle.Large
        )
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                showLoader(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.alertsState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                it?.let(::handleAlertsState)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.surveyFinishedState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                if (it) {
                    navigateToConfirmScreen()
                }
            }
        }
    }

    private fun handleAlertsState(alert: ZEMTSurveyDiscoverAlerts) {
        when (alert) {
            ZEMTSurveyDiscoverAlerts.SurveyDownloadError -> {
                errorDialog.showErrorDialog(REQUEST_KEY_DOWNLOAD_SURVEY_ERROR)
            }

            ZEMTSurveyDiscoverAlerts.MaxNumberOfNeutralAnswers -> {
                discoverDialogs.showMaxNumberOfNeutralAnswersReached()
            }

            ZEMTSurveyDiscoverAlerts.SurveyAnswerSynchronizerError -> {
                errorDialog.showErrorDialog(REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR)
            }

            ZEMTSurveyDiscoverAlerts.SavePoint -> discoverDialogs.showSavePoint()

            ZEMTSurveyDiscoverAlerts.FirstTime -> discoverDialogs.showFirstTime()
            ZEMTSurveyDiscoverAlerts.TakeBreak -> discoverDialogs.showTakeBreak()
        }
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

    private fun navigateToConfirmScreen() {
        findNavController().navigate(
            ZEMTSurveyDiscoverFragmentDirections
                .actionZEMTSurveyDiscoverFragmentToZEMTHomeConfirmFragment(true)
        )
    }

    override fun onResume() {
        viewModel.onCancelReminderIfExits()
        super.onResume()
    }

    override fun onPause() {
        if (!viewModel.uiState.value.userHasFinishSurvey) {
            viewModel.onScheduleReminder()
        }
        super.onPause()
    }

    companion object {
        internal const val REQUEST_KEY_ARE_YOU_SURE_WANT_TO_LEAVE =
            "REQUEST_KEY_ARE_YOU_SURE_WANT_TO_LEAVE"
        internal const val REQUEST_KEY_MAX_NUMBER_OF_NEUTRAL_ANSWERS =
            "REQUEST_KEY_MAX_NUMBER_OF_NEUTRAL_ANSWERS"
        internal const val REQUEST_KEY_DOWNLOAD_SURVEY_ERROR = "REQUEST_KEY_DOWNLOAD_SURVEY_ERROR"
        internal const val REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR =
            "REQUEST_KEY_SYNCHRONIZE_SURVEY_ERROR"
        internal const val REQUEST_KEY_SAVE_POINT = "REQUEST_KEY_SAVE_POINT"
        internal const val REQUEST_KEY_FIRST_TIME = "REQUEST_KEY_FIRST_TIME"
    }

}