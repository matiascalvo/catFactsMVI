package com.matiascalvo.catfacts.data.repo

import com.matiascalvo.catfacts.data.api.ServiceProvider
import com.matiascalvo.catfacts.data.model.CatFact
import io.reactivex.Single

class CatFactRepository(private val serviceProvider: ServiceProvider) {

    fun getFact(): Single<CatFact> {
        return serviceProvider.provideCatFactService().getFact().map { it.toCatFact() }
    }
}
