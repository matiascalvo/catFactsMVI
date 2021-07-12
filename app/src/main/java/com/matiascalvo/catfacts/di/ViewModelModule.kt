package com.matiascalvo.catfacts.di

import com.matiascalvo.catfacts.ui.catfact.CatFactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CatFactViewModel(get()) }
}
