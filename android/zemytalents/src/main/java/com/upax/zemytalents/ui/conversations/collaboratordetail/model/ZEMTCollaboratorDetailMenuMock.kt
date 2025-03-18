package com.upax.zemytalents.ui.conversations.collaboratordetail.model

import com.upax.zccommon.extensions.EMPTY
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.conversations.ZEMTConversation
import com.upax.zemytalents.ui.conversations.collaboratordetail.ZEMTCollaboratorDetailItem
import com.upax.zemytalents.ui.conversations.collaboratordetail.ZEMTCollaboratorDetailSection

internal object ZEMTCollaboratorDetailMenuMock {
    fun getMenuList(
        getString: (Int) -> String,
        conversationList: List<ZEMTConversation>
    ): List<ZEMTCollaboratorDetailSection> {
        val conversationRecord = conversationList.map {
            ZEMTCollaboratorDetailItem(
                title = it.name,
                icon = it.icon,
                id = it.conversationId
            )
        }
        return listOf(
            ZEMTCollaboratorDetailSection(
                sectionTitle = getString(R.string.zemt_collaborator_detail_talent_section_talent_title),
                navigationType = ZEMTCollaboratorNavigationType.GoToTalent,
                items = listOf(
                    ZEMTCollaboratorDetailItem(
                        title = getString(R.string.zemt_collaborator_detail_talent_section_talent_item),
                        icon = R.drawable.zemt_ic_person_play,
                        id = String.EMPTY
                    )
                )
            ),
            ZEMTCollaboratorDetailSection(
                sectionTitle = getString(R.string.zemt_collaborator_detail_talent_section_conversation_record_title),
                navigationType = ZEMTCollaboratorNavigationType.GoToConversation,
                items = conversationRecord
            )
        )
    }
}