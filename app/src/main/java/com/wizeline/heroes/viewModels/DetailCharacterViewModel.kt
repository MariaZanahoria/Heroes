package com.wizeline.heroes.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.heroes.model.CharacterInfo
import com.wizeline.heroes.model.Characters
import com.wizeline.heroes.repositories.CatalogRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailCharacterViewModel(private val catalogRepository: CatalogRepository = CatalogRepository()) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _characterDetail = MutableLiveData<CharacterInfo>()
    val characterDetail: LiveData<CharacterInfo>
        get() = _characterDetail

    private val _comics = MutableLiveData<Characters>()
    val comics: LiveData<Characters>
        get() = _comics

    private val _series = MutableLiveData<Characters>()
    val series: LiveData<Characters>
        get() = _series

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getCharacterDetail(id: Int) {
        compositeDisposable.add(
            catalogRepository.requestCharacter(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { it.data?.results }
                .doOnError { error -> _errorMessage.postValue(error.message) }
                .forEach {
                    it?.firstOrNull()?.let { characterInfo ->
                        _characterDetail.postValue(characterInfo)
                    }
                    compositeDisposable.addAll(
                        catalogRepository.requestCharacterComics(id)
                            .subscribe(
                                { _comics.postValue(it) },
                                { error -> _errorMessage.postValue(error.message) }
                            ),
                        catalogRepository.requestCharacterSeries(id)
                            .subscribe(
                                { _series.postValue(it) },
                                { error -> _errorMessage.postValue(error.message) }
                            )
                    )
                })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}