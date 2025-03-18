package com.upax.zemytalents.ui.modules.confirm.survey

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.textview.MaterialTextView
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.expose.ZCDSResourceUtils
import com.upax.zemytalents.R
import com.upax.zcdesignsystem.R as RDS

internal class ZEMTSurveyConfirmDialogs(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {
    private val tag = ZCDSSimpleDialogFragment.TAG

    fun showAreYouSureToExit() {
        if (isDialogVisible()) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Warning.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.EXIT_SURVEY)
            .setLottieAnimation(lottieJson)
            .setTitle(R.string.zemt_are_you_sure_want_to_leave_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_are_you_sure_want_to_leave_description)
            .setPositiveButton(R.string.zemt_leave)
            .setNegativeButton(R.string.zemt_cancel)
            .build()
            .show(fragmentManager, tag)
    }

    fun showSavedProgress() {
        if (isDialogVisible()) return
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.SAVED_PROGRESS)
            .setLottieAnimation(ZCDSLottieCatalog.Star)
            .setTitle(R.string.zemt_save_point_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_confirm_save_point_description)
            .setPositiveButton(R.string.zemt_continue)
            .build()
            .show(fragmentManager, tag)
    }

    fun showSurveyEnd() {
        if (isDialogVisible()) return
        val animation = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.TipsZeus.filename
        )
        val dialog = ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.CONFIRM_SURVEY_END)
            .setLottieAnimation(animation)
            .setTitle(R.string.zemt_you_are_in_the_end_of_the_survey)
            .setCancelable(false)
            .setMessage(R.string.zemt_you_are_in_the_end_of_the_confirm_survey)
            .setPositiveButton(R.string.zemt_continue)
            .setNegativeButton(R.string.zemt_cancel)
            .build().also { it.show(fragmentManager, tag) }
        fragmentManager.executePendingTransactions()
        dialog.dialog?.findViewById<MaterialTextView>(RDS.id.zds_txt_message)?.maxLines =
            Integer.MAX_VALUE
    }

    fun showNextTalent(remainingTalents: Int) {
        if (isDialogVisible()) return
        val animation = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.TipsZeus.filename
        )
        val title = when (remainingTalents) {
            1 -> context.getString(R.string.zemt_one_talent_more)
            2 -> context.getString(R.string.zemt_two_talent_more)
            3 -> context.getString(R.string.zemt_three_talent_more)
            else -> context.getString(R.string.zemt_good_job_remaining_talents, remainingTalents)
        }
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.NEXT_QUESTION)
            .setLottieAnimation(animation)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(R.string.zemt_confirm_answers_or_cancel)
            .setPositiveButton(R.string.zemt_continue)
            .setNegativeButton(R.string.zemt_cancel)
            .build()
            .show(fragmentManager, tag)
    }

    private fun isDialogVisible(): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    object RequestKeys {
        const val EXIT_SURVEY = "EXIT_SURVEY"
        const val CONFIRM_SURVEY_END = "CONFIRM_SURVEY_END"
        const val NEXT_QUESTION = "NEXT_QUESTION"
        const val SAVED_PROGRESS = "SAVED_PROGRESS"
    }
}