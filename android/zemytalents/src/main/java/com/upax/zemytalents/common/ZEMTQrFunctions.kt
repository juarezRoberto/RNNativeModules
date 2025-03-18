package com.upax.zemytalents.common

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.FileProvider
import androidx.core.view.doOnNextLayout
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.upax.zcsessioninfo.domain.model.ZCSIUser
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.conversations.ZEMTQrData
import com.upax.zemytalents.ui.conversations.qr.ZEMTShareableQr
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal object ZEMTQrFunctions {

    fun ZCSIUser.generateQrData(): ZEMTQrData = ZEMTQrData(
        collaboratorId = zeusId,
        employeeNumber = employeeNumber,
        companyId = companyIdentifier
    )

    fun LocalDateTime.parse(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return this.format(formatter)
    }

    fun String.toLocalDateTime(): LocalDateTime {
        val dateOnly = this.substring(0, 19)
        val formatter = DateTimeFormatter.ofPattern(DATE_SERVICE_FORMAT)
        return LocalDateTime.parse(dateOnly, formatter)
    }

    fun LocalDateTime.parseDateForService(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_SERVICE_FORMAT)
        return this.format(formatter)
    }

    fun openShareDialog(context: Context, qrInfo: Bitmap) {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "qrData.png")
        FileOutputStream(file).use {
            qrInfo.compress(Bitmap.CompressFormat.PNG, 100, it)
            it.flush()
        }
        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.fileprovider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/png"
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.zemt_qr_share)
            )
        )
    }

    suspend fun Fragment.composableToBitmap(
        content: @Composable () -> Unit
    ): Bitmap {
        return withContext(Dispatchers.Main) {
            val deferred = CompletableDeferred<Bitmap>()
            val activity = requireActivity()

            val container = FrameLayout(activity).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            val composeView = ComposeView(activity).apply {
                setContent { content() }
            }

            container.addView(composeView)
            activity.addContentView(container, container.layoutParams)

            composeView.doOnNextLayout {
                composeView.postDelayed({
                    val bitmap: Bitmap = composeView.drawToBitmap()
                    deferred.complete(bitmap)
                    container.removeView(composeView)
                    (container.parent as? ViewGroup)?.removeView(container)

                }, 100)
            }

            deferred.await()
        }
    }

    fun Fragment.shareQrCode(userData: ZCSIUser) {
        lifecycleScope.launch {
            val bitmap = composableToBitmap {
                ZEMTShareableQr(userData = userData)
            }
            openShareDialog(requireActivity(), bitmap)
        }
    }

    const val DATE_FORMAT = "dd/MM/yyyy"
    const val DATE_SERVICE_FORMAT = "yyyy-MM-dd HH:mm:ss"
}

