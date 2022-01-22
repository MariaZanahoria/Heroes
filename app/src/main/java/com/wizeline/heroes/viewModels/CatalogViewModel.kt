package com.wizeline.heroes.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.heroes.model.Characters
import com.wizeline.heroes.repositories.CatalogRepository
import io.reactivex.disposables.CompositeDisposable

class CharactersViewModel : ViewModel() {
    private val catalogRepository = CatalogRepository()
    private val compositeDisposable = CompositeDisposable()
    private val _characters = MutableLiveData<Characters>()
    val characters: LiveData<Characters> get() = _characters

    fun getCharacters() {
        compositeDisposable.add(
            catalogRepository.requestCharacters().subscribe {
                //add data to a liveData
                _characters.postValue(it)
            }
        )
    }
}