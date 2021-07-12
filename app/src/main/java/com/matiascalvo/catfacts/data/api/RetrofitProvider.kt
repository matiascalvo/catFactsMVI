package com.matiascalvo.catfacts.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface RetrofitProvider {
    fun provide(): Retrofit
}

class RetrofitProviderImpl(
    private val clientProvider: OkHttpProvider,
    private val moshiProvider: MoshiProvider,
    private val catFactApiUrlProvider: CatFactApiUrlProvider
) :
    RetrofitProvider {
    override fun provide(): Retrofit {
        return Retrofit.Builder()
            .client(clientProvider.provide())
            .baseUrl(catFactApiUrlProvider.provide())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshiProvider.provide()))
            .build()
    }
}
