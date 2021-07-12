package com.matiascalvo.catfacts.data.remote

import com.matiascalvo.catfacts.data.model.CatFact
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatFactRemote(
    val fact: String
) {
    fun toCatFact() = CatFact(fact)
}
