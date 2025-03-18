package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.conversations.ZEMTCollaboratorInChargeEntityMother
import com.upax.zemytalents.rules.ZEMTConversationsDatabaseTestRule
import com.upax.zemytalents.rules.ZEMTCoroutinesTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ZEMTRoomCollaboratorsRepositoryTest {

    @get:Rule(order = 0)
    val coroutinesTestRule = ZEMTCoroutinesTestRule()

    @get:Rule(order = 1)
    val databaseTestRule = ZEMTConversationsDatabaseTestRule()

    @Test
    fun `findCollaboratorsByName should return all coincidences`() = runTest {
        populateDatabaseWithCollaborators()
        val repository = ZEMTRoomCollaboratorsRepository(
            databaseTestRule.collaboratorsInChargeDao()
        )
        val nameToFound = "Juarez"

        val collaboratorsFound = repository.findCollaboratorsByName(nameToFound)

        assertTrue(collaboratorsFound.isNotEmpty())
        assertTrue(collaboratorsFound.all { it.name.contains(nameToFound) })
    }

    @Test
    fun `findCollaboratorsByName should return case-insensitive matches for the query`() = runTest {
        populateDatabaseWithCollaborators()
        val repository = ZEMTRoomCollaboratorsRepository(
            databaseTestRule.collaboratorsInChargeDao()
        )
        val nameToFound = "jUaREz"

        val collaboratorsFound = repository.findCollaboratorsByName(nameToFound)

        assertTrue(collaboratorsFound.isNotEmpty())
        assertTrue(collaboratorsFound.all { it.name.contains("Juarez") })
    }

    @Test
    fun `findCollaboratorsByName should ignore spaces`() = runTest {
        populateDatabaseWithCollaborators()
        val repository = ZEMTRoomCollaboratorsRepository(
            databaseTestRule.collaboratorsInChargeDao()
        )
        val nameToFound = "   jUaREz   "

        val collaboratorsFound = repository.findCollaboratorsByName(nameToFound)

        assertTrue(collaboratorsFound.isNotEmpty())
        assertTrue(collaboratorsFound.all { it.name.contains("Juarez") })
    }

    @Test
    fun `findCollaboratorsByName should return empty list if there is no coincidences`() = runTest {
        populateDatabaseWithCollaborators()
        val repository = ZEMTRoomCollaboratorsRepository(
            databaseTestRule.collaboratorsInChargeDao()
        )
        val nameToFound = "pablo"

        val collaboratorsFound = repository.findCollaboratorsByName(nameToFound)

        assertTrue(collaboratorsFound.isEmpty())
    }

    private suspend fun populateDatabaseWithCollaborators() {
        databaseTestRule.collaboratorsInChargeDao().insertAll(
            listOf(
                ZEMTCollaboratorInChargeEntityMother.apply(
                    collaboratorId = "1",
                    name = "Carlos Antonio"
                ),
                ZEMTCollaboratorInChargeEntityMother.apply(
                    collaboratorId = "2",
                    name = "Roberto Juarez"
                ),
                ZEMTCollaboratorInChargeEntityMother.apply(
                    collaboratorId = "3",
                    name = "Manuel Soberanis"
                ),
                ZEMTCollaboratorInChargeEntityMother.apply(
                    collaboratorId = "4",
                    name = "Alejandro Cerecedo"
                ),
                ZEMTCollaboratorInChargeEntityMother.apply(
                    collaboratorId = "5",
                    name = "Juan Juarez"
                )
            )
        )
    }

}