package com.matiascalvo.catfacts.ui.catfact

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class FakeCatFactViewModel : CatFactViewModel(FakeCatFactUseCase()) {

    private val testAction = TestObserver<CatFactAction>()
    val testState = PublishSubject.create<CatFactState>()

    init {
        actions.subscribe(testAction)
        disposables += testState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}

class FakeCatFactUseCase : CatFactUseCase {
    override fun getFact(): Single<String> = Single.just("test")
}
