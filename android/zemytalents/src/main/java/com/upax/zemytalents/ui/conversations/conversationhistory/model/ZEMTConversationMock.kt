package com.upax.zemytalents.ui.conversations.conversationhistory.model

internal object ZEMTConversationMock {
    fun getConversationList(): List<ZEMTConversationState> = listOf(
        ZEMTConversationState.Uncompleted(
            date = "14 Nov 2024",
            title = "No realizada",
            message = "No te preocupes sí no la pudiste realizar, siempre hay tiempo para crear nuevas conversaciones.",
        ),
        ZEMTConversationState.Completed(
            date = "14 Oct 2024",
            comment = "Debemos de tener más sesiones de apoyo",
            phrase = "",
        ),
        ZEMTConversationState.Completed(
            date = "14 Sep 2024",
            comment = "Debemos de tener más sesiones de apoyo",
            phrase = """“Pregúntale acerca de alguna competencia reciente en la cual haya participado o que haya ganado.”""",
        ),
    )
}
