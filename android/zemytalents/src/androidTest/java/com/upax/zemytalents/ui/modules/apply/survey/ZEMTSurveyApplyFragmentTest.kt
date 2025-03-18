package com.upax.zemytalents.ui.modules.apply.survey

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.upax.zcdesignsystem.R
import com.upax.zcdesignsystem.widget.button.ZCDSButton
import com.upax.zemytalents.rules.SurveyApplyDatabaseRule
import com.upax.zemytalents.ui.modules.apply.survey.ZEMTSurveyApplyFragmentMockWebServerDispatcher.Companion.NAME_TALENTS_IN_ORDER
import com.upax.zemytalents.ui.modules.apply.survey.ZEMTSurveyApplyFragmentMockWebServerDispatcher.Companion.TOTAL_ANSWERS_TALENTS
import com.upax.zemytalents.ui.modules.apply.survey.ZEMTSurveyApplyFragmentMockWebServerDispatcher.Companion.TOTAL_SURVEY_TALENTS
import com.upax.zemytalents.utils.DIProviderRule
import com.upax.zemytalents.utils.MockWebServerRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ZEMTSurveyApplyFragmentTest {

    @get:Rule(order = 0)
    val mockWebServerRule = MockWebServerRule(ZEMTSurveyApplyFragmentMockWebServerDispatcher())

    @get:Rule(order = 1)
    val diProviderRule = DIProviderRule()

    @get:Rule(order = 2)
    val surveyApplyRule = SurveyApplyDatabaseRule()

    @get:Rule(order = 3)
    val composeTestRule = createEmptyComposeRule()

    private lateinit var fragmentScenario: FragmentScenario<ZEMTSurveyApplyFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer<ZEMTSurveyApplyFragment>(
            themeResId = R.style.Theme_ZCDSAppTheme
        )
        composeTestRule.waitForIdle()
    }

    @After
    fun cleanUp() {
        fragmentScenario.close()
    }

    @Test
    fun surveyTalentsShouldContainAllTalentsFromTheService() {
        composeTestRule.onNodeWithTag("survey talents")
            .performScrollToIndex(TOTAL_SURVEY_TALENTS - 1)

    }

    @Test
    fun surveyTalentsShouldBeDisplayedInTheOrderInWhichTheServiceReturnsThem() {
        (0..<TOTAL_SURVEY_TALENTS).forEach {
            composeTestRule.onNodeWithTag("survey talents")
                .performScrollToIndex(it)
            composeTestRule.onNodeWithTag(it.toString())
                .assert(hasAnyChild(hasTestTag(NAME_TALENTS_IN_ORDER[it])))
        }
    }

    @Test
    fun whenUserFinishedAnswerTalentThenAlertAppears() = runTest {
        repeat(TOTAL_ANSWERS_TALENTS) {
            composeTestRule.onNodeWithTag("positive button").performClick()
        }
        composeTestRule.awaitIdle()
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("talent applied")
            assertNotNull(dialog)
        }
    }

    @Test
    fun whenUserFinishedAnswerTalentThenNextTalentIsSelected() = runTest {
        val indexNextTalent = 1
        composeTestRule.onNodeWithTag(indexNextTalent.toString()).assertIsNotSelected()
        repeat(TOTAL_ANSWERS_TALENTS) {
            composeTestRule.onNodeWithTag("positive button").performClick()
        }
        composeTestRule.awaitIdle()
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("talent applied")
            dialog?.view?.findViewById<ZCDSButton>(R.id.zds_btn_primary)?.performClick()
        }
        composeTestRule.onNodeWithTag(indexNextTalent.toString()).assertIsSelected()
    }

    @Test
    fun whenUserFinishedAnswerAllSurveyThenAlertAppears() = runTest {
        repeat(TOTAL_SURVEY_TALENTS) {
            repeat(TOTAL_ANSWERS_TALENTS) {
                composeTestRule.onNodeWithTag("positive button").performClick()
            }
            composeTestRule.awaitIdle()
            fragmentScenario.onFragment { fragment ->
                val dialog = fragment.childFragmentManager.findFragmentByTag("talent applied")
                dialog?.view?.findViewById<ZCDSButton>(R.id.zds_btn_primary)?.performClick()
            }
        }
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("survey finished")
            assertNotNull(dialog)
        }
    }

    @Test
    fun whenOnBackPressedThenAlertAppears() = runTest {
        fragmentScenario.onFragment { fragment ->
            fragment.requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.awaitIdle()
        fragmentScenario.onFragment { fragment ->
            val dialog = fragment.childFragmentManager.findFragmentByTag("sure exit")
            assertNotNull(dialog)
        }
    }

}
