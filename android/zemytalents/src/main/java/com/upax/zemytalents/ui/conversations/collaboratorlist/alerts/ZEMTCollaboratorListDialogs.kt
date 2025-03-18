package com.upax.zemytalents.ui.conversations.collaboratorlist.alerts

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zemytalents.R

internal class ZEMTCollaboratorListDialogs(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {
    fun showContinueProgressDialog(key: String = RequestKeys.CONTINUE_PROGRESS) {
        if (isDialogVisible()) return
        val lottie = ZCDSLottieCatalog.Info
        ZCDSSimpleDialogFragment.Builder(context, key)
            .setLottieAnimation(lottie)
            .setTitle(context.getString(R.string.zemt_dialog_title_saved_progress))
            .setCancelable(false)
            .setMessage(context.getString(R.string.zemt_dialog_message_continue_conversation))
            .setPositiveButton(context.getString(R.string.zemt_dialog_positive_button_continue))
            .setNegativeButton(context.getString(R.string.zemt_dialog_negative_button_cancel))
            .build()
            .show(fragmentManager, ZCDSSimpleDialogFragment.TAG)
    }

    private fun isDialogVisible(): Boolean {
        return fragmentManager.findFragmentByTag(ZCDSSimpleDialogFragment.TAG) != null
    }

    internal data object RequestKeys {
        const val CONTINUE_PROGRESS = "CONTINUE_PROGRESS"
    }
}