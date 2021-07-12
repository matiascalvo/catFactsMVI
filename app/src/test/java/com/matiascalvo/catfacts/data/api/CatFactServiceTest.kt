package com.matiascalvo.catfacts.data.api

import com.matiascalvo.catfacts.data.remote.CatFactRemote
import com.matiascalvo.catfacts.utils.ServiceTest
import org.junit.Test
import org.koin.test.inject

private const val FACT = "this is a fact"
private const val FACT_JSON = "{\"fact\":\"$FACT\",\"length\":${FACT.length}}"

class CatFactServiceTest : ServiceTest() {

    private val sut: CatFactService by inject()

    @Test
    fun `when getFact then request is made to the correct url`() {
        enqueueContent(FACT_JSON)

        sut.getFact().test()

        assertRequestSentTo("fact", "GET")
    }

    @Test
    fun `when getFact then fact is parsed correctly`() {
        enqueueContent(FACT_JSON)

        val result = sut.getFact().test()

        result.assertResult(CatFactRemote(fact = FACT))
    }
}
