package com.matiascalvo.catfacts.data.api

private const val DEFAULT_URL = "https://catfact.ninja/"

interface CatFactApiUrlProvider {
    fun provide(): String
}

class CatFactApiUrlProviderImpl : CatFactApiUrlProvider {
    override fun provide() = DEFAULT_URL
}
