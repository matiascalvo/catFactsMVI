package com.matiascalvo.catfacts.di

import com.matiascalvo.catfacts.ui.catfact.CatFactUseCase
import com.matiascalvo.catfacts.ui.catfact.CatFactUseCaseImpl
import com.matiascalvo.catfacts.data.repo.CatFactRepository
import com.matiascalvo.catfacts.data.api.*
import org.koin.dsl.module

val appModule = module {
    factory<OkHttpProvider> { OkHttpProviderImpl() }
    factory<MoshiProvider> { MoshiProviderImpl() }
    factory<CatFactApiUrlProvider> { CatFactApiUrlProviderImpl() }
    factory<RetrofitProvider> { RetrofitProviderImpl(get(), get(), get()) }
    factory<CatFactService> { get<RetrofitProvider>().provide().create(CatFactService::class.java) }
    factory { RetrofitProviderImpl(get(), get(), get()) }
    single { ServiceProvider(get()) }
    factory { CatFactRepository(get()) }
    factory<CatFactUseCase> { CatFactUseCaseImpl(get()) }
}
