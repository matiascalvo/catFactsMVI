package com.matiascalvo.catfacts.data.api

import com.matiascalvo.catfacts.data.remote.CatFactRemote
import io.reactivex.Single
import retrofit2.http.GET

interface CatFactService {

    @GET("fact")
    fun getFact(): Single<CatFactRemote>
}
