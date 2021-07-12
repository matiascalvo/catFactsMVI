package com.matiascalvo.catfacts.ui.catfact

import com.ww.roxie.BaseState

data class CatFactState(
    val loading: Boolean = false,
    val fact: String = "",
    val displayError: Boolean = false,
) : BaseState
