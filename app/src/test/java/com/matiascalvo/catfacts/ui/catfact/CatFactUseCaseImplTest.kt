package com.matiascalvo.catfacts.ui.catfact

import com.matiascalvo.catfacts.data.model.CatFact
import com.matiascalvo.catfacts.data.repo.CatFactRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val FACT = "this is a fact"

class CatFactUseCaseImplTest {

    private val repo: CatFactRepository = mock()

    private val sut = CatFactUseCaseImpl(repo)

    @Before
    fun setUp() {
        val fact = CatFact(fact = FACT)
        whenever(repo.getFact()).thenReturn(Single.just(fact))
    }

    @Test
    fun `whenever usecase is called then repo is called`() {
        sut.getFact()

        verify(repo).getFact()
    }

    @Test
    fun `whenever usecase is called then correct answer is returned`() {

        val result = sut.getFact()

        result.test().assertResult(FACT)
    }
}
