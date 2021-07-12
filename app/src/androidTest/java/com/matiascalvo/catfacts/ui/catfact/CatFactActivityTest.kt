package com.matiascalvo.catfacts.ui.catfact

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.matiascalvo.catfacts.R
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

class CatFactActivityTest : KoinTest {

    private val viewModel = FakeCatFactViewModel()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java, true, false)

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel<CatFactViewModel> { viewModel }
            }
        )
    }

    @Test
    fun initialState() {
        val state = CatFactState()
        givenState(state)

        activityRule.launchActivity(null)

        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        assertEquals(viewModel.observableState.value, state)
    }

    @Test
    fun loadingState() {
        val state = CatFactState(loading = true)
        givenState(state)

        activityRule.launchActivity(null)

        onView(withId(R.id.loadingIndicator)).check(matches(isDisplayed()))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.main_view_empty_state_text)))
        onView(withId(R.id.getFactButton)).check(matches(not(isEnabled())))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun errorState() {
        val state = CatFactState(displayError = true)
        givenState(state)

        activityRule.launchActivity(null)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.main_view_empty_state_text)))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(isDisplayed()))
    }

    @Test
    fun factLoadedState() {
        val fact = "Even cat facts need to be tested"
        val state = CatFactState(fact = fact)
        viewModel.testState.onNext(state)

        activityRule.launchActivity(null)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    private fun givenState(state: CatFactState) {
        viewModel.testState.onNext(state)
    }
}
