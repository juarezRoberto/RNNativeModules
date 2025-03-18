package com.upax.zemytalents.domain.repositories

interface ZEMTSurveyDiscoverReminder {

    fun scheduleReminder()

    fun cancelPendingReminders()

}