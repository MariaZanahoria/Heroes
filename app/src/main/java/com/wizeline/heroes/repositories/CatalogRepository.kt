package com.wizeline.heroes.repositories

import com.wizeline.heroes.*
import com.wizeline.heroes.model.Characters
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CatalogRepository(private val service: HeroesServices = NetworkClient().getServices()) {

    fun requestCharacters(): Observable<Characters> {
        return service.getCharacters(TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }

    fun requestCharacter(id: String): Observable<Characters> {
        return service.getComicsOfCharacter(id, TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }
}