package com.matiascalvo.catfacts.data.api

class ServiceProvider(retrofitProvider: RetrofitProvider) {
    private val catFactService by lazy {
        retrofitProvider.provide().create(CatFactService::class.java)
    }

    fun provideCatFactService(): CatFactService = catFactService
}
