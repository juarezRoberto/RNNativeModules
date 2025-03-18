package com.upax.zemytalents.ui.conversations.collaboratorlist.model

import com.upax.zemytalents.domain.models.conversations.ZEMTCollaboratorInCharge
import kotlin.random.Random

internal object ZEMTCollaboratorsMockData {
    val collaboratorsMockList = listOf(
        ZEMTCollaboratorInCharge(
            name = "David Martinez",
            photoUrl = "",
            collaboratorId = "1",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "Pedro Estevez",
            photoUrl = "",
            collaboratorId = "2",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "Romina Meléndez",
            photoUrl = "",
            collaboratorId = "3",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "Victor Ontiveros",
            photoUrl = "",
            collaboratorId = "4",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "5",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "6",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "7",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "8",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "9",
            talentsCompleted = Random.nextBoolean()
        ),
        ZEMTCollaboratorInCharge(
            name = "RitaPerez",
            photoUrl = "",
            collaboratorId = "10",
            talentsCompleted = Random.nextBoolean()
        ),
    )

    val collaboratorsMockListLarge = collaboratorsMockList + collaboratorsMockList.map {
        it.copy(
            collaboratorId = Random.nextInt().toString()
        )
    }
}