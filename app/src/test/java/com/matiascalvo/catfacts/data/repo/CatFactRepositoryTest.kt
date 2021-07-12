package com.matiascalvo.catfacts.data.repo

import com.matiascalvo.catfacts.data.api.CatFactService
import com.matiascalvo.catfacts.data.api.ServiceProvider
import com.matiascalvo.catfacts.data.model.CatFact
import com.matiascalvo.catfacts.data.remote.CatFactRemote
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val FACT = "this is a fact"

class CatFactRepositoryTest {
    private val factRemote = CatFactRemote(FACT)
    private val service: CatFactService = mock()
    private val serviceProvider: ServiceProvider = mock()

    private val sut = CatFactRepository(serviceProvider)

    @Before
    fun setUp() {
        whenever(serviceProvider.provideCatFactService()).thenReturn(service)
        whenever(service.getFact()).thenReturn(Single.just(factRemote))
    }

    @Test
    fun `when getFact then service is called`() {
        sut.getFact()

        verify(service).getFact()
    }

    @Test
    fun `given service returns a factRemote when getFact then catFact is returned`() {
        val result = sut.getFact()

        result.test().assertResult(CatFact(FACT))
    }
}
