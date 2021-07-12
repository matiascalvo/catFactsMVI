package com.matiascalvo.catfacts.ui.catfact

sealed class CatFactChange {
    object Loading : CatFactChange()
    data class FactLoaded(val fact: String) : CatFactChange()
    object Error : CatFactChange()
}
