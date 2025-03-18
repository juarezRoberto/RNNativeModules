package com.upax.zemytalents.ui.shared

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.dialog.ZCDSSimpleDialogFragment
import com.upax.zcdesignsystem.expose.ZCDSLottieCatalog
import com.upax.zcdesignsystem.expose.ZCDSResourceUtils
import com.upax.zemytalents.R

internal class ZEMTErrorDialog(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {

    private val tag = ZCDSSimpleDialogFragment.TAG

    fun showErrorDialog(requestKey: String) {
        if (isDialogVisible()) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Error.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, requestKey)
            .setLottieAnimation(lottieJson)
            .setTitle(R.string.zemt_error_title)
            .setCancelable(false)
            .setMessage(R.string.zemt_error_description)
            .setPositiveButton(R.string.zemt_accept)
            .build()
            .show(fragmentManager, tag)
    }

    fun showErrorDialog(
        requestKey: String = String.EMPTY,
        @StringRes title: Int = R.string.zemt_error_title,
        @StringRes message: Int = R.string.zemt_error_description,
        @StringRes positiveButtonText: Int = R.string.zemt_accept,
        @StringRes negativeButtonText: Int = R.string.zemt_empty,
    ) {
        if (isDialogVisible()) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, ZCDSLottieCatalog.Error.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, requestKey)
            .setLottieAnimation(lottieJson)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(positiveButtonText)
            .setNegativeButton(negativeButtonText)
            .build()
            .show(fragmentManager, tag)
    }

    fun showErrorDialog(
        requestKey: String = String.EMPTY,
        lottie: ZCDSLottieCatalog = ZCDSLottieCatalog.Error,
        title: String = String.EMPTY,
        message: String = String.EMPTY,
        positiveButtonText: String = String.EMPTY,
        negativeButtonText: String = String.EMPTY,
    ) {
        if (isDialogVisible()) return
        val lottieJson = ZCDSResourceUtils.getLottieAnimationJSONFromAssets(
            context, lottie.filename
        )
        ZCDSSimpleDialogFragment
            .Builder(context, requestKey)
            .setLottieAnimation(lottieJson)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(positiveButtonText)
            .setNegativeButton(negativeButtonText)
            .build()
            .show(fragmentManager, tag)
    }

    private fun isDialogVisible(): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

}