package com.upax.zemytalents.data.mock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.upax.zemytalents.R
import com.upax.zemytalents.data.local.icons.ZEMTIconParser
import com.upax.zemytalents.domain.models.conversations.ZEMTPhrase
import com.upax.zemytalents.ui.shared.models.ZEMTCheckGroupItem

@Composable
internal fun ZEMTMockPhraseList() = listOf(
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_phrase_record_item0),
        ZEMTIconParser.NONE.iconId,
        "0"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_phrase_record_item1),
        ZEMTIconParser.NONE.iconId,
        "1"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_phrase_record_item2),
        ZEMTIconParser.NONE.iconId,
        "2"
    ),
    ZEMTCheckGroupItem(
        stringResource(R.string.zemt_collaborator_detail_talent_section_phrase_record_item3),
        ZEMTIconParser.NONE.iconId,
        "3"
    ),
)

internal fun getPhrasesServiceMock(conversationId: String) = listOf(
    ZEMTPhrase(
        id = "1",
        phrase = "Pregúntale por alguien que haya conocido recientemente.",
        conversationId = conversationId
    ),
    ZEMTPhrase(
        id = "2",
        phrase = "Pídele que te platique acerca de experiencias donde conocer a gente nueva haya abierto alguna nueva oportunidad.",
        conversationId = conversationId
    ),
    ZEMTPhrase(
        id = "3",
        phrase = "Pregúntale que tan importante es para el/ella contar con amigos para toda la vida.",
        conversationId = conversationId
    ),
    ZEMTPhrase(
        id = "4",
        phrase = "Pídele que te platique acerca de cómo le hace para profundizar su relación con alguna persona.",
        conversationId = conversationId
    )
)
