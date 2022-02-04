package com.wizeline.heroes.repositories

import com.wizeline.heroes.*
import com.wizeline.heroes.BuildConfig.API_KEY
import com.wizeline.heroes.model.Characters
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CatalogRepository(private val service: HeroesServices = NetworkClient().getServices()) {

    fun requestCharacters(): Observable<Characters> {
        return service.getCharacters(TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }

    fun requestCharacterComics(id: Int): Observable<Characters> {
        return service.getCharacterComics(id, TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }

    fun requestCharacterSeries(id: Int): Observable<Characters> {
        return service.getCharacterSeries(id, TS, API_KEY, HASH)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }

    suspend fun getSearchedCharacters(nameToSearch: String): Characters {
        return service.getSearchedCharacters(nameToSearch, TS, API_KEY, HASH)
    }
}