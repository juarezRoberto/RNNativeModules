package com.upax.zemytalents.ui.modules.apply.survey

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.expose.ZCDSResourceUtils
import com.upax.zemytalents.R

internal class ZEMTSurveyApplyDialogs(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {

    fun showAreYouSureToExit() {
        val tag = "sure exit"
        if (isDialogVisible(tag)) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Warning.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.EXIT_SURVEY)
            .setLottieAnimation(lottieJson)
            .setTitle(R.string.zemt_are_you_sure_want_to_leave_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_are_you_sure_want_to_leave_survey_apply)
            .setPositiveButton(R.string.zemt_leave)
            .setNegativeButton(R.string.zemt_cancel)
            .build()
            .show(fragmentManager, tag)
    }

    fun showTalentApplied(
        talent: String,
        remainingTalents: Int
    ) {
        val tag = "talent applied"
        if (isDialogVisible(tag)) return
        val animation = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Star.filename
        )
        val dialog = ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.NEXT_QUESTION)
            .setLottieAnimation(animation)
            .setTitle(context.getString(R.string.zemt_talent_applied_title, talent.lowercase()))
            .setCancelable(false)
            .setPositiveButton(R.string.zemt_continue)
        if (remainingTalents > 0) {
            val text = if (remainingTalents == 1) {
                context.getString(R.string.zemt_talent_applied_description_one)
            } else {
                context.getString(R.string.zemt_talent_applied_description, remainingTalents)
            }
            dialog.setMessage(text)
        }
        dialog.build().show(fragmentManager, tag)
    }

    fun showSurveyFinished() {
        val tag = "survey finished"
        if (isDialogVisible(tag)) return
        val animation = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.TipsZeus.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.SURVEY_FINISHED)
            .setLottieAnimation(animation)
            .setTitle(R.string.zemt_talent_apply_finished_title)
            .setMessage(R.string.zemt_talent_apply_finished_description)
            .setCancelable(false)
            .setPositiveButton(R.string.zemt_continue)
            .build()
            .show(fragmentManager, tag)
    }

    fun showSurveyInProgress() {
        val tag = "survey in progress"
        if (isDialogVisible(tag)) return
        val animation = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Star.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, RequestKeys.SURVEY_IN_PROGRESS)
            .setLottieAnimation(animation)
            .setTitle(R.string.zemt_save_point_title)
            .setMessage(R.string.zemt_apply_save_point_description)
            .setCancelable(false)
            .setPositiveButton(R.string.zemt_continue)
            .build()
            .show(fragmentManager, tag)
    }

    private fun isDialogVisible(tag: String): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    object RequestKeys {
        const val EXIT_SURVEY = "EXIT_SURVEY"
        const val NEXT_QUESTION = "NEXT_QUESTION"
        const val SURVEY_FINISHED = "SURVEY_FINISHED"
        const val SURVEY_IN_PROGRESS = "SURVEY_IN_PROGRESS"
    }
}