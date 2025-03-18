package com.upax.zemytalents.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule(
    private val mockDispatcher: Dispatcher
) : TestWatcher() {

    private lateinit var server: MockWebServer

    override fun starting(description: Description?) {
        server = MockWebServer()
        server.start(8080)
        server.dispatcher = mockDispatcher
    }

    override fun finished(description: Description?) {
        server.shutdown()
    }

}