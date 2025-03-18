package com.upax.zemytalents.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.utils.observeThemeManagerForStatusBarColor
import com.upax.zcdesignsystem.widget.ZCDSTopAppBar
import com.upax.zemytalents.R
import com.upax.zemytalents.data.repository.ZEMTInMemoryTalentsRepository
import com.upax.zemytalents.data.worker.ZEMTSurveyDiscoverNotificationWorker.Companion.ARG_COME_FROM_NOTIFICATION_DISCOVER_SURVEY
import com.upax.zemytalents.databinding.ZemtActivityHostBinding
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdListUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetTalentsCompletedByIdUseCase
import com.upax.zemytalents.domain.usecases.ZEMTGetUserTalentsUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetCollaboratorsInChargeUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationListUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetConversationsHistoryUseCase
import com.upax.zemytalents.domain.usecases.conversations.ZEMTGetPhrasesUseCase
import kotlinx.coroutines.launch
import com.upax.zcdesignsystem.R as RDS

class ZEMTHostActivity : AppCompatActivity() {

    private lateinit var binding: ZemtActivityHostBinding
    private val viewModel: ZEMTHostActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ZemtActivityHostBinding.inflate(layoutInflater)
//        addStartDestination()
//        setContentView(binding.root)
//        initComponents()
        lifecycleScope.launch {
            viewModel.onSuccess.flowWithLifecycle(lifecycle).collect { success ->
                if (success) {
                    addStartDestination()
                    setContentView(binding.root)
                    initComponents()
                }
            }
        }
        viewModel.configureEnvironment()
    }

    private fun addStartDestination() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.zemtNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val startDestination = if (comeFromDiscoverSurveyNotification()) {
            R.id.ZEMTStartFragment
        } else {
            R.id.ZEMTMenuTalentFragment
        }
        navController.graph =
            navController.navInflater.inflate(R.navigation.zemt_main_graph).apply {
                setStartDestination(startDestination)
            }
    }

    private fun comeFromDiscoverSurveyNotification(): Boolean {
        return intent.extras?.getBoolean(
            ARG_COME_FROM_NOTIFICATION_DISCOVER_SURVEY, false
        ) ?: false
    }

    private fun initComponents() {
        enableEdgeToEdgeScreen()
        observeThemeManagerForStatusBarColor()
        binding.zemtTopAppBar.apply {
            setTitle(getString(R.string.zemt_my_talents))
            setTopAppBarBackgroundColor(
                ContextCompat.getColor(
                    this@ZEMTHostActivity,
                    RDS.color.zcds_white
                )
            )
            setNavigationClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun enableEdgeToEdgeScreen() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.zemtTopAppBar) { v, insets ->
            val statusBars = insets.getInsets(
                WindowInsetsCompat.Type.statusBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(top = statusBars.top)
            insets
        }
    }

    fun hideAppBar() {
        binding.zemtTopAppBar.visibility = View.GONE
    }

    fun updateAppBarTitle(title: String) {
        binding.zemtTopAppBar.apply {
            visibility = View.VISIBLE
            setTitle(title)
            setSubtitle(null)
            setTitlesStyle(ZCDSTopAppBar.TitlesStyle.Small)
            disableTopAppBarIcons()
        }
    }

    fun updateAppBarTitle(title: String, subtitle: String, style: ZCDSTopAppBar.TitlesStyle) {
        binding.zemtTopAppBar.apply {
            visibility = View.VISIBLE
            setTitle(title)
            setSubtitle(subtitle)
            setTitlesStyle(style)
            disableTopAppBarIcons()
        }
    }

    private fun disableTopAppBarIcons() {
        binding.zemtTopAppBar.showButtonRight(false)
    }

    fun updateAppBarTitle(
        title: String,
        subtitle: String = String.EMPTY,
        style: ZCDSTopAppBar.TitlesStyle = ZCDSTopAppBar.TitlesStyle.Small,
        iconAction: Pair<Int, (View) -> Unit>? = null
    ) {
        binding.zemtTopAppBar.apply {
            visibility = View.VISIBLE
            setTitle(title)
            setSubtitle(subtitle)
            setTitlesStyle(style)
            if (iconAction != null) {
                setButtonRightIcon(iconAction.first)
                setButtonRightClickListener(iconAction.second)
                showButtonRight(true)
            } else disableTopAppBarIcons()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ZEMTInMemoryTalentsRepository.destroyInstance()
        ZEMTGetCollaboratorsInChargeUseCase.reset()
        ZEMTGetConversationListUseCase.reset()
        ZEMTGetPhrasesUseCase.reset()
        ZEMTGetTalentsCompletedByIdUseCase.reset()
        ZEMTGetTalentsCompletedByIdListUseCase.reset()
        ZEMTGetUserTalentsUseCase.reset()
        ZEMTGetConversationsHistoryUseCase.reset()
    }
}