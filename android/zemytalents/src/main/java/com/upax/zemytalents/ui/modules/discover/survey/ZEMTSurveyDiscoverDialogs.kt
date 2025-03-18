package com.upax.zemytalents.ui.modules.discover.survey

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.fragment.app.FragmentManager
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.expose.ZCDSResourceUtils
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_ARE_YOU_SURE_WANT_TO_LEAVE
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_FIRST_TIME
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_MAX_NUMBER_OF_NEUTRAL_ANSWERS
import com.upax.zemytalents.ui.modules.discover.survey.ZEMTSurveyDiscoverFragment.Companion.REQUEST_KEY_SAVE_POINT

internal class ZEMTSurveyDiscoverDialogs(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {

    private val tag = ZCDSSimpleDialogFragment.TAG

    fun showAreYouSureWantToLeave() {
        if (isDialogVisible(tag)) return
        ZCDSSimpleDialogFragment
            .Builder(context, REQUEST_KEY_ARE_YOU_SURE_WANT_TO_LEAVE)
            .setLottieAnimation(ZCDSLottieCatalog.Warning)
            .setTitle(R.string.zemt_are_you_sure_want_to_leave_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_are_you_sure_want_to_leave_description)
            .setPositiveButton(R.string.zemt_leave)
            .setNegativeButton(R.string.zemt_cancel)
            .build()
            .show(fragmentManager, tag)
    }

    fun showMaxNumberOfNeutralAnswersReached() {
        val tag = "max number neutral answers"
        if (isDialogVisible(tag)) return
        ZCDSSimpleDialogFragment
            .Builder(context, REQUEST_KEY_MAX_NUMBER_OF_NEUTRAL_ANSWERS)
            .setLottieAnimation(ZCDSLottieCatalog.Warning)
            .setTitle(R.string.zemt_attention)
            .setCancelable(false)
            .setMessage(R.string.zemt_max_number_neutral_answers)
            .setPositiveButton(R.string.zemt_ok)
            .build()
            .show(fragmentManager, tag)
    }

    fun showSavePoint() {
        if (isDialogVisible(tag)) return
        ZCDSSimpleDialogFragment
            .Builder(context, REQUEST_KEY_SAVE_POINT)
            .setLottieAnimation(ZCDSLottieCatalog.Star)
            .setTitle(R.string.zemt_save_point_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_save_point_description)
            .setPositiveButton(R.string.zemt_continue)
            .build()
            .show(fragmentManager, tag)
    }

    fun showFirstTime() {
        val tag = "first time"
        if (isDialogVisible(tag)) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.TipsZeus.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, REQUEST_KEY_FIRST_TIME)
            .setLottieAnimation(lottieJson)
            .setTitle(R.string.zemt_first_time_survey_discover_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_first_time_survey_discover_description)
            .setPositiveButton(R.string.zemt_continue)
            .build()
            .show(fragmentManager, tag)
    }


    fun showTakeBreak() {
        val tag = "take break"
        if (isDialogVisible(tag)) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Star.filename
        )

        ZCDSSimpleDialogFragment
            .Builder(context, REQUEST_KEY_TAKE_BREAK)
            .setLottieAnimation(lottieJson)
            .setTitle(R.string.zemt_take_break_title)
            .setCancelable(false)
            .setMessage(getTakeBreakMessage())
            .setPositiveButton(R.string.zemt_take_break_positive_button)
            .setNegativeButton(R.string.zemt_take_break_negative_button)
            .build()
            .show(fragmentManager, tag)
    }

    private fun getTakeBreakMessage(): SpannableStringBuilder {
        val textInBold = context.getString(R.string.zemt_take_break_time)
        val rawText = context.getString(R.string.zemt_take_break_description, textInBold)
        val startIndex = rawText.indexOf(textInBold)
        val endIndex = startIndex + textInBold.length
        val spannable = SpannableStringBuilder(rawText)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun isDialogVisible(tag: String): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    companion object {
        internal const val REQUEST_KEY_TAKE_BREAK = "REQUEST_KEY_TAKE_BREAK"
    }

}