package com.wizeline.heroes.repositories

import com.wizeline.heroes.*
import com.wizeline.heroes.model.Characters
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CatalogRepository {
    private val service = NetworkClient().getServices()

    fun requestCharacters(): Observable<Characters> {
        return service.getCharacters(TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }
}