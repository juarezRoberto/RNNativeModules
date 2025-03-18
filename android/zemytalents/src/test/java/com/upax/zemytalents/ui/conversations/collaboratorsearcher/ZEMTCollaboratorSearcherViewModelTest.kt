package com.upax.zemytalents.ui.conversations.collaboratorsearcher

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.upax.zemytalents.data.repository.fake.ZEMTFakeCollaboratorsRepository
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInChargeMother
import com.upax.zemytalents.domain.usecases.conversations.ZEMTConversationProgressUseCase
import com.upax.zemytalents.ui.conversations.collaboratorlist.model.ZEMTCollaboratorListViewType
import com.upax.zemytalents.utils.ZHCFlowUtils.avoidFirstUiStateEvent
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ZEMTCollaboratorSearcherViewModelTest {

    private val collaborators = listOf(
        ZEMTCollaboratorInChargeMother.apply(name = "Carlos Antonio"),
        ZEMTCollaboratorInChargeMother.apply(name = "Roberto Juarez"),
        ZEMTCollaboratorInChargeMother.apply(name = "Manuel Soberanis"),
        ZEMTCollaboratorInChargeMother.apply(name = "Alejandro Cerecedo"),
        ZEMTCollaboratorInChargeMother.apply(name = "Juan Juarez"),
    )

    private fun getViewModel(
        viewType: ZEMTCollaboratorListViewType = ZEMTCollaboratorListViewType.entries.random()
    ): ZEMTCollaboratorSearcherViewModel {
        return ZEMTCollaboratorSearcherViewModel(
            ZEMTFakeCollaboratorsRepository(collaborators),
            mockk<ZEMTConversationProgressUseCase>(),
            SavedStateHandle(mutableMapOf("viewType" to viewType))
        )
    }

    @Test
    fun `at the beginning collaborators should be null`() {
        assertNull(getViewModel().collaborators.value)
    }

    @Test
    fun `when onSearchTextChange found collaborators then update the collaborators`() = runTest {
        val viewModel = getViewModel()
        viewModel.collaborators.avoidFirstUiStateEvent().test {
            viewModel.onSearchTextChange("Carlos")
            assertEquals(listOf(collaborators.first()), awaitItem())
        }
    }

    @Test
    fun `when onSearchTextChange not found collaborators then update collaborators as empty list `() =
        runTest {
            val viewModel = getViewModel()
            viewModel.collaborators.avoidFirstUiStateEvent().test {
                viewModel.onSearchTextChange("Jose")
                assertEquals(emptyList<ZEMTCollaboratorInCharge>(), awaitItem())
            }
        }

    @Test
    fun `quickly onSearchTextChange are debounced`() = runTest {
        val viewModel = getViewModel()
        viewModel.collaborators.avoidFirstUiStateEvent().test {
            viewModel.onSearchTextChange("M")
            viewModel.onSearchTextChange("Ma")
            viewModel.onSearchTextChange("Manu")
            viewModel.onSearchTextChange("Manue")

            assertEquals(listOf(collaborators[2]), awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when onSearchTextChange contains empty query then emit null value`() = runTest {
        val viewModel = getViewModel()
        viewModel.collaborators.avoidFirstUiStateEvent().test {
            viewModel.onSearchTextChange("Carlos")
            assertEquals(listOf(collaborators.first()), awaitItem())
            viewModel.onSearchTextChange("")
            assertNull(awaitItem())
        }
    }

    @Test
    fun `onCollaboratorSelected update state to TalentsNoCompleted when collaborator has not completed talents`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.collaboratorAction.avoidFirstUiStateEvent().test {
                val collaborator = ZEMTCollaboratorInChargeMother.apply(talentsCompleted = false)
                viewModel.onCollaboratorSelected(collaborator)
                assertEquals(ZEMTCollaboratorSearcherActionsState.TalentsNoCompleted, awaitItem())
            }
        }

    @Test
    fun `onCollaboratorSelected update state to NavigateToDetail when collaborator has talents and view type is TALENTS`() =
        runTest {
            val viewModel = getViewModel(viewType = ZEMTCollaboratorListViewType.TALENTS)
            viewModel.collaboratorAction.avoidFirstUiStateEvent().test {
                val collaborator = ZEMTCollaboratorInChargeMother.apply(talentsCompleted = true)
                viewModel.onCollaboratorSelected(collaborator)
                assertEquals(ZEMTCollaboratorSearcherActionsState.NavigateToDetail, awaitItem())
            }
        }

    @Test
    fun `onCollaboratorSelected update state to NavigateToMakeConversation when collaborator has talents and view type is MAKE_CONVERSATION`() =
        runTest {
            val viewModel = getViewModel(viewType = ZEMTCollaboratorListViewType.MAKE_CONVERSATION)
            viewModel.collaboratorAction.avoidFirstUiStateEvent().test {
                val collaborator = ZEMTCollaboratorInChargeMother.apply(talentsCompleted = true)
                viewModel.onCollaboratorSelected(collaborator)
                assertEquals(
                    ZEMTCollaboratorSearcherActionsState.NavigateToMakeConversation,
                    awaitItem()
                )
            }
        }

}