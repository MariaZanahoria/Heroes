package com.wizeline.heroes.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.heroes.model.Characters
import com.wizeline.heroes.repositories.CatalogRepository
import io.reactivex.disposables.CompositeDisposable

class DetailCharacterViewModel(private val catalogRepository: CatalogRepository = CatalogRepository()) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _comics = MutableLiveData<Characters>()
    val comics: LiveData<Characters>
        get() = _comics

    private val _series = MutableLiveData<Characters>()
    val series: LiveData<Characters>
        get() = _series

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getCharacterComics(id: Int) {
        compositeDisposable.add(
            catalogRepository.requestCharacterComics(id)
                .subscribe({
                    //add data to a liveData
                    _comics.postValue(it)
                },
                    { error ->
                        _errorMessage.postValue(error.message)
                    })
        )
    }

    fun getCharacterSeries(id: Int) {
        compositeDisposable.add(
            catalogRepository.requestCharacterSeries(id)
                .subscribe({
                    //add data to a liveData
                    _comics.postValue(it)
                },
                    { error ->
                        _errorMessage.postValue(error.message)
                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}