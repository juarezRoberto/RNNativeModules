package com.upax.zemytalents.ui.modules.discover

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.upax.zcdesignsystem.R
import com.upax.zcdesignsystem.widget.button.ZCDSButton
import com.upax.zemytalents.rules.SurveyDiscoverDatabaseRule
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment
import com.upax.zemytalents.utils.DIProviderRule
import com.upax.zemytalents.utils.MockWebServerRule
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ZEMTSurveyDiscoverFragmentTest {

    @get:Rule(order = 0)
    val mockWebServerRule = MockWebServerRule(ZEMTSurveyDiscoverFragmentMockWebServerDispatcher())

    @get:Rule(order = 1)
    val diProviderRule = DIProviderRule()

    @get:Rule(order = 2)
    val surveyDiscoverRule = SurveyDiscoverDatabaseRule()

    @get:Rule(order = 3)
    val composeTestRule = createEmptyComposeRule()

    private lateinit var fragmentScenario: FragmentScenario<ZEMTSurveyDiscoverFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer<ZEMTSurveyDiscoverFragment>(
            themeResId = R.style.Theme_ZCDSAppTheme
        )
        composeTestRule.waitForIdle()
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(0L)
    }

    @After
    fun cleanUp() {
        fragmentScenario.close()
    }

    @Test
    fun leftAndRightQuestionsTextShouldBeDisplayedEqualTService() {
        composeTestRule.onNodeWithTag("left text")
            .assertTextEquals("Me siento triste cuando no le caigo bien a alguien")
        composeTestRule.onNodeWithTag("right text")
            .assertTextEquals("Los demás acuden a mí para verificar que su información sea exacta, bien documentada")
    }

    @Test
    fun whenUserEntersThenDisplayAlert() {
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("first time")
            assertNotNull(dialog)
        }
    }

    @Test
    fun whenUserResponse31timesNeutralAnswerThenAlertShouldBeDisplayed() {
        skipFirstAlert()
        repeat(31) {
            val tagNeutralAnswer = "2"
            composeTestRule.onNodeWithTag(tagNeutralAnswer).performClick()
            composeTestRule.mainClock.advanceTimeBy(50L)
            composeTestRule.onNodeWithTag("confirm answer").performClick()
        }
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager
                .findFragmentByTag("max number neutral answers")
            assertNotNull(dialog)
        }
    }

    private fun skipFirstAlert() {
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("first time")
            dialog?.view?.findViewById<ZCDSButton>(R.id.zds_btn_primary)?.performClick()
        }
    }

}