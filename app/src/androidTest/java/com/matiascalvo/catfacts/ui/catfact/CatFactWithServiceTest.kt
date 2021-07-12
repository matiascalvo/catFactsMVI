package com.matiascalvo.catfacts.ui.catfact

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.matiascalvo.catfacts.R
import com.matiascalvo.catfacts.TestUrlProvider
import com.matiascalvo.catfacts.di.viewModelModule
import com.matiascalvo.catfacts.data.api.CatFactApiUrlProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.net.HttpURLConnection

private const val FACT = "this is a fact"
private const val FACT_JSON = "{\"fact\":\"$FACT\"}"

class CatFactWithServiceTest : KoinTest {

    private var server: MockWebServer = MockWebServer()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java, true, false)

    @Before
    fun setUp() {
        server.start(TestUrlProvider.PORT)
        loadKoinModules(
            listOf(
                viewModelModule,
                module {
                    factory<CatFactApiUrlProvider> { TestUrlProvider() }
                }
            )
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun initialState() {
        activityRule.launchActivity(null)

        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun errorState() {
        enqueueContent(FACT_JSON, HttpURLConnection.HTTP_BAD_REQUEST)
        activityRule.launchActivity(null)

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.main_view_empty_state_text)))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }

    @Test
    fun factLoadedState() {
        enqueueContent(FACT_JSON, HttpURLConnection.HTTP_OK)

        activityRule.launchActivity(null)

        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(FACT)))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    private fun enqueueContent(content: String, code: Int = HttpURLConnection.HTTP_OK) {
        server.enqueue(MockResponse().setResponseCode(code).setBody(content))
    }
}
