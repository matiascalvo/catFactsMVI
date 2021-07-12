package com.matiascalvo.catfacts.data.api

import com.matiascalvo.catfacts.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor

interface OkHttpProvider {
    fun provide(): OkHttpClient
}

class OkHttpProviderImpl : OkHttpProvider {
    override fun provide(): OkHttpClient {
        val builder = Builder()
        addLoggingInterceptor(builder)
        return builder.build()
    }

    private fun addLoggingInterceptor(builder: Builder) {
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }
    }
}
