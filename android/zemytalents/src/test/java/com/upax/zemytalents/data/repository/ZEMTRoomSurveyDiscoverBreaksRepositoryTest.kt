package com.upax.zemytalents.data.repository

import app.cash.turbine.test
import com.upax.zemytalents.data.local.database.entity.ZEMTBreakDiscoverEntityMother
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTGroupQuestionIndexDiscover
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import com.upax.zemytalents.rules.ZEMTMyTalentsDatabaseTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomSurveyDiscoverBreaksRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTMyTalentsDatabaseTestRule()

    val repository by lazy {
        ZEMTRoomSurveyDiscoverBreaksRepository(
            breakDao = databaseTestRule.breakDao()
        )
    }

    @Test
    fun `getNextBreak should be return flow with null value when there is no data`() = runTest {
        repository.getNextBreak().test {
            assertNull(awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `getNextBreak should return break with lowest groupQuestionIndex and seen false`() =
        runTest {
            val breaks = listOf(
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 66, seen = false),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 132, seen = false),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 190, seen = false)
            )
            databaseTestRule.breakDao().insertAll(breaks)
            repository.getNextBreak().test {
                assertEquals(66, awaitItem()!!.indexGroupQuestion.value)
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `getNextBreak should skip breaks with seen true and return the one with lowest groupQuestionIndex and seen false`() =
        runTest {
            val breaks = listOf(
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 66, seen = true),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 132, seen = false),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 190, seen = false)
            )
            databaseTestRule.breakDao().insertAll(breaks)
            repository.getNextBreak().test {
                assertEquals(132, awaitItem()!!.indexGroupQuestion.value)
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `markBreakAsSeen should update getNextBreak with break with lowest groupQuestionIndex and seen false`() =
        runTest {
            val breaks = listOf(
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 66, seen = true),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 132, seen = false),
                ZEMTBreakDiscoverEntityMother.apply(groupQuestionIndex = 190, seen = false)
            )
            databaseTestRule.breakDao().insertAll(breaks)
            repository.getNextBreak().test {
                awaitItem()
                repository.markBreakAsSeen(ZEMTGroupQuestionIndexDiscover(132))
                assertEquals(190, awaitItem()!!.indexGroupQuestion.value)
                ensureAllEventsConsumed()
            }
        }

}