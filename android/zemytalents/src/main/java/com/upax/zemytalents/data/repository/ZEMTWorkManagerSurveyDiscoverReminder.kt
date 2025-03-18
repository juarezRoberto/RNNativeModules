package com.upax.zemytalents.data.repository

import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.upax.zemytalents.data.worker.ZEMTSurveyDiscoverNotificationWorker
import com.upax.zemytalents.domain.repositories.ZEMTSurveyDiscoverReminder
import java.util.concurrent.TimeUnit

internal class ZEMTWorkManagerSurveyDiscoverReminder(
    private val workManager: WorkManager,
    private val notificationManager: NotificationManagerCompat
) : ZEMTSurveyDiscoverReminder {

    private val tag = ZEMTWorkManagerSurveyDiscoverReminder::class.java.simpleName
    private val notificationId = 13112024

    override fun scheduleReminder() {
        val data = Data.Builder()
        data.putInt(ZEMTSurveyDiscoverNotificationWorker.ARG_NOTIFICATION_ID, notificationId)
        val workRequest =
            OneTimeWorkRequest.Builder(ZEMTSurveyDiscoverNotificationWorker::class.java)
                .setInitialDelay(1, TimeUnit.HOURS)
                .addTag(tag)
                .setInputData(data.build())
                .build()
        workManager.enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, workRequest)
    }

    override fun cancelPendingReminders() {
        notificationManager.cancel(notificationId)
        workManager.cancelAllWorkByTag(tag)
    }

}