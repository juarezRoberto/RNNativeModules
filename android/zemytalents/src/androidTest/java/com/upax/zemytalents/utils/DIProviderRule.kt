package com.upax.zemytalents.utils

import androidx.test.espresso.IdlingRegistry
import com.upax.zemytalents.di.ZEMTApiServiceProvider
import com.upax.zemytalents.di.ZEMTDataProvider
import com.upax.zemytalents.di.ZEMTRepositoryProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DIProviderRule : TestWatcher() {

    private val resource = OkHttp3IdlingResource
        .create("OkHttp", ZEMTApiServiceProvider.okHttpClientTest)

    override fun starting(description: Description?) {
        ZEMTDataProvider.isTestingEnvironment = true
        ZEMTRepositoryProvider.isTestingEnvironment = true
        IdlingRegistry.getInstance().register(resource)
    }

    override fun finished(description: Description?) {
        ZEMTDataProvider.isTestingEnvironment = false
        ZEMTRepositoryProvider.isTestingEnvironment = false
        IdlingRegistry.getInstance().unregister(resource)
    }

}