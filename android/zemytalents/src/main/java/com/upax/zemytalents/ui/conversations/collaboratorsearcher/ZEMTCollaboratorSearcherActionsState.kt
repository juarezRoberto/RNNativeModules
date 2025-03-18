package com.upax.zemytalents.ui.conversations.collaboratorsearcher

internal sealed interface ZEMTCollaboratorSearcherActionsState {

    data object TalentsNoCompleted :
        ZEMTCollaboratorSearcherActionsState

    data object NavigateToDetail :
        ZEMTCollaboratorSearcherActionsState

    data object NavigateToMakeConversation :
        ZEMTCollaboratorSearcherActionsState

}