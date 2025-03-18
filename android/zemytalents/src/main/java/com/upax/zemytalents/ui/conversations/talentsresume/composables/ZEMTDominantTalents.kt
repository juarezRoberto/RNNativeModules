package com.upax.zemytalents.ui.conversations.talentsresume.composables

import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.conversations.talentsresume.model.ZEMTTalentsResumeType
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId
import com.upax.zemytalents.ui.modules.shared.mock.ZEMTMockModulesData
import com.upax.zemytalents.ui.shared.composables.ZEMTDropdownGroup
import com.upax.zemytalents.ui.shared.composables.ZEMTText
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfile
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileStages
import com.upax.zemytalents.ui.shared.composables.talentsprofile.ZEMTTalentsProfileUserUiModel
import com.upax.zcdesignsystem.R as DesignSystem

@Composable
internal fun ZEMTDominantTalents(
    userName: String,
    userProfileUrl: String,
    talentList: List<ZEMTTalent>,
    viewType: ZEMTTalentsResumeType,
    modifier: Modifier = Modifier,
    onTalentClick: (ZEMTTalent?) -> Unit = {}
) {
    val userFirstName = getFirstName(userName)


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        if (viewType == ZEMTTalentsResumeType.MY_TALENTS) {
            ZEMTText(
                text = stringResource(R.string.zemt_greeting_user, userFirstName),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyXLarge,
                textStyle = Typeface.BOLD
            )

            ZEMTText(
                text = stringResource(R.string.zemt_talents_dominant_more_info_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                style = DesignSystem.style.TextAppearance_ZCDSApp_BodyMedium,
            )
        } else {
            val charSequence: String =
                LocalContext.current.getString(
                    R.string.zemt_user_profile_instructions,
                    userFirstName
                )
            val startIndex = charSequence.indexOf(userFirstName)
            val endIndex = startIndex + userFirstName.length

            val annotatedText = buildAnnotatedString {
                append(charSequence)
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold),
                    start = startIndex,
                    end = endIndex
                )
            }
            ZEMTText(
                text = annotatedText,
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        }

        if (talentList.isNotEmpty()) {
            ZEMTTalentsProfile(
                stage = ZEMTTalentsProfileStages.Complete(talentList.take(5)),
                data = ZEMTTalentsProfileUserUiModel(
                    userName = userName,
                    userProfileUrl = userProfileUrl,
                ),
                onTalentClick = onTalentClick,
                onTalentFocusListener = {}, modifier = Modifier.padding(top = 32.dp)
            )

            val nextTalents = talentList.drop(5)
            if (nextTalents.isNotEmpty()) {
                ZEMTText(
                    stringResource(R.string.zemt_more_talents),
                    style = com.upax.zcdesignsystem.R.style.TextAppearance_ZCDSApp_Header04,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )

                nextTalents.forEachIndexed { index, talent ->
                    ZEMTDropdownGroup(
                        groupTitle = "${index + 6}. ${talent.name}",
                        groupIcon = talent.getIconFromId(),
                        isOpenable = false,
                        onGroupClick = { onTalentClick(talent) },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

private fun getFirstName(userName: String): String {
    return userName.split(" ").firstOrNull().orEmpty()
}


@Preview
@Composable
private fun ZEMTDominantTalentsPreview() {
    ZEMTDominantTalents(
        userName = "David Martinez",
        userProfileUrl = "",
        talentList = ZEMTMockModulesData.getTalents(),
        onTalentClick = {},
        viewType = ZEMTTalentsResumeType.COLLABORATOR_TALENTS
    )
}

@Preview
@Composable
private fun ZEMTDominantTalentsPreview3() {
    ZEMTDominantTalents(
        userName = "David Martinez",
        userProfileUrl = "",
        talentList = ZEMTMockModulesData.getTalents() + ZEMTMockModulesData.getTalents(),
        onTalentClick = {},
        viewType = ZEMTTalentsResumeType.COLLABORATOR_TALENTS
    )
}

@Preview
@Composable
private fun ZEMTDominantTalentsPreview2() {
    ZEMTDominantTalents(
        userName = "David Martinez",
        userProfileUrl = "",
        talentList = ZEMTMockModulesData.getTalents(),
        onTalentClick = {},
        viewType = ZEMTTalentsResumeType.MY_TALENTS
    )
}