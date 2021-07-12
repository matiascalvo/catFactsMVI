package com.matiascalvo.catfacts.data.api

import org.junit.Assert.*
import org.junit.Test

class CatFactApiUrlProviderImplTest {
    private val sut = CatFactApiUrlProviderImpl()

    @Test
    fun `when provide is called then correct api url is returned`() {
        val result = sut.provide()

        assertEquals("https://catfact.ninja/", result)
    }
}
