package com.matiascalvo.catfacts.ui.catfact

import com.matiascalvo.catfacts.data.repo.CatFactRepository
import io.reactivex.Single

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImpl(private val catFactRepository: CatFactRepository) : CatFactUseCase {

    override fun getFact(): Single<String> =
        catFactRepository.getFact().map { it.fact }
}
