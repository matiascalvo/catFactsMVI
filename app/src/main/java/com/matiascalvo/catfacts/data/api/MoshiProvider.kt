package com.matiascalvo.catfacts.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

interface MoshiProvider {
    fun provide(): Moshi
}

class MoshiProviderImpl : MoshiProvider {
    override fun provide(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
}
