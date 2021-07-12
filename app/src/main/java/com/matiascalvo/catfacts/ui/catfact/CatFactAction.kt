package com.matiascalvo.catfacts.ui.catfact

import com.ww.roxie.BaseAction

sealed class CatFactAction : BaseAction {
    object GetFactButtonClicked : CatFactAction()
}
