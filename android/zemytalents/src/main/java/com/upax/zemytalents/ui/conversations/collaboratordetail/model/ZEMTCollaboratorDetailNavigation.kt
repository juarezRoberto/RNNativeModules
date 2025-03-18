package com.upax.zemytalents.ui.conversations.collaboratordetail.model

sealed interface ZEMTCollaboratorDetailNavigation {
    data class GoToTalent(val subtitle: String) : ZEMTCollaboratorDetailNavigation
    data class GoToConversation(val conversationTitle: String, val conversationId: String) :
        ZEMTCollaboratorDetailNavigation
}

enum class ZEMTCollaboratorNavigationType {
    GoToTalent,
    GoToConversation
}