package com.upax.zemytalents.data.worker

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.upax.zccommon.utils.notifications.ZCCNotificationsHelper
import com.upax.zemytalents.R
import com.upax.zemytalents.ui.ZEMTHostActivity

class ZEMTSurveyDiscoverNotificationWorker(
    appContext: Context, workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val notificationId = this.inputData.getInt(ARG_NOTIFICATION_ID, 1)
        val intent = Intent(applicationContext, ZEMTHostActivity::class.java)
            .putExtra(ARG_COME_FROM_NOTIFICATION_DISCOVER_SURVEY, true)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ZCCNotificationsHelper.Builder(applicationContext)
            .setId(notificationId)
            .setTitle(applicationContext.getString(R.string.zemt_complete_discover_test_title))
            .setContent(applicationContext.getString(R.string.zemt_complete_discover_test_description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setIntent(intent)
            .build().show()
        return Result.success()
    }

    companion object {
        internal const val ARG_NOTIFICATION_ID = "ARG_NOTIFICATION_ID"
        internal const val ARG_COME_FROM_NOTIFICATION_DISCOVER_SURVEY = "ARG_COME_FROM_NOTIFICATION_DISCOVER_SURVEY"
    }

}