package com.matiascalvo.catfacts.utils

import com.matiascalvo.catfacts.data.api.CatFactApiUrlProvider
import com.matiascalvo.catfacts.di.appModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

abstract class ServiceTest : AutoCloseKoinTest() {

    private var server: MockWebServer = MockWebServer()

    @After
    open fun afterEach() {
        server.shutdown()
    }

    protected fun enqueueContent(content: String, code: Int = HttpURLConnection.HTTP_OK) {
        server.enqueue(MockResponse().setResponseCode(code).setBody(content))
    }

    protected fun assertRequestSentTo(url: String, method: String = "GET") {
        val request = takeRequest()
        assertEquals("/$url", request?.path)
        assertEquals(method, request?.method)
    }

    @Before
    fun setUp() {
        server.start(TestUrlProvider.PORT)
        startKoin {
            allowOverride(true)
            modules(
                appModule,
                module {
                    factory<CatFactApiUrlProvider> { TestUrlProvider() }
                }
            )
        }
    }

    protected fun takeRequest() = server.takeRequest(1, TimeUnit.SECONDS)
}
