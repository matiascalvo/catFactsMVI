package com.matiascalvo.catfacts.ui.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.matiascalvo.catfacts.RxTestSchedulerRule
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.lang.RuntimeException

class CatFactViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTestSchedulerRule = RxTestSchedulerRule()

    private val catFactUsecase = mock<CatFactUseCase>()
    private val observer = mock<Observer<CatFactState>>()

    lateinit var sut: CatFactViewModel

    private val loadingState = CatFactState(loading = true)

    @Before
    fun setUp() {
        sut = CatFactViewModel(catFactUsecase)
        sut.observableState.observeForever(observer)
    }

    @After
    fun tearDown() {
        sut.observableState.removeObserver(observer)
    }

    @Test
    fun `given fact successfully loaded, when action getFact is received, then State contains fact`() {
        val fact = "programmers love cats"

        val successfulState = CatFactState(loading = false, fact = fact)
        whenever(catFactUsecase.getFact()).thenReturn(Single.just(fact))

        sut.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        inOrder(observer) {
            verify(observer).onChanged(CatFactState())
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successfulState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given fact failed to load, then action GetFact receiverd, then state contains error`() {
        val errorState = CatFactState(displayError = true)
        whenever(catFactUsecase.getFact()).thenReturn(Single.error(RuntimeException()))

        sut.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        inOrder(observer) {
            verify(observer).onChanged(CatFactState())
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}
