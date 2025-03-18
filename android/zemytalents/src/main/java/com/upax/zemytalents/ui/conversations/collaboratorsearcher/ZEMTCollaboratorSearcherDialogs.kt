package com.upax.zemytalents.ui.conversations.collaboratorsearcher

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.expose.ZCDSResourceUtils
import com.upax.zemytalents.R

internal class ZEMTCollaboratorSearcherDialogs(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {

    fun showCollaboratorHasNoCompletedTalents(collaboratorName: String) {
        val tag = "no talents"
        if (isDialogVisible(tag)) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Info.filename
        )
        val message = context.getString(
            R.string.zemt_dialog_message_remind_talent_test,
            collaboratorName
        )
        ZCDSSimpleDialogFragment
            .Builder(context, KEY_NO_COMPLETED_TALENTS)
            .setLottieAnimation(lottieJson)
            .setCancelable(true)
            .setMessage(message)
            .setPositiveButton(R.string.zemt_dialog_button_remind_talent_test)
            .build()
            .show(fragmentManager, tag)
    }

    private fun isDialogVisible(tag: String): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    companion object {
        internal const val KEY_NO_COMPLETED_TALENTS = "KEY_NO_COMPLETED_TALENTS"
    }

}