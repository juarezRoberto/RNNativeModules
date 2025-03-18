package com.upax.zemytalents.domain.models.conversations

data class ZEMTCollaboratorInCharge(
    val collaboratorId: String,
    val name: String,
    val photoUrl: String,
    val talentsCompleted: Boolean
)
