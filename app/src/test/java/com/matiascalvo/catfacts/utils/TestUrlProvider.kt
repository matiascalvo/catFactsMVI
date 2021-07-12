package com.matiascalvo.catfacts.utils

import com.matiascalvo.catfacts.data.api.CatFactApiUrlProvider

class TestUrlProvider : CatFactApiUrlProvider {
    override fun provide() = "http://localhost:$PORT"

    companion object {
        const val PORT = 8500
    }
}
