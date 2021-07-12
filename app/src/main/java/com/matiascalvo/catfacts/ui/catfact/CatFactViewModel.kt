package com.matiascalvo.catfacts.ui.catfact

import android.util.Log
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

open class CatFactViewModel(private val catFactUseCase: CatFactUseCase) :
    BaseViewModel<CatFactAction, CatFactState>() {
    override val initialState = CatFactState()

    private val reducer: Reducer<CatFactState, CatFactChange> = { state, change ->
        when (change) {
            is CatFactChange.Loading -> state.copy(loading = true, displayError = false)
            is CatFactChange.FactLoaded -> state.copy(
                loading = false,
                displayError = false,
                fact = change.fact
            )
            is CatFactChange.Error -> state.copy(loading = false, displayError = true)
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val getFactChange =
            actions.ofType(CatFactAction.GetFactButtonClicked::class.java)
                .switchMap {
                    catFactUseCase.getFact()
                        .subscribeOn(Schedulers.io())
                        .toObservable()
                        .map<CatFactChange> { fact ->
                            CatFactChange.FactLoaded(fact)
                        }
                        .doOnError { error ->
                            Log.e("pampa", error.stackTraceToString())
                        }
                        .onErrorReturn { CatFactChange.Error }
                        .startWith(CatFactChange.Loading)
                }
        disposables += getFactChange.scan(initialState, reducer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}
