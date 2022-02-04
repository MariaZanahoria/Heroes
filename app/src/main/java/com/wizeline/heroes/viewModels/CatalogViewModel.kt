package com.wizeline.heroes.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.heroes.model.Characters
import com.wizeline.heroes.repositories.CatalogRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class CharactersViewModel(private val catalogRepository: CatalogRepository = CatalogRepository()) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _characters = MutableLiveData<Characters>()
    val characters: LiveData<Characters>
        get() = _characters

    private val _searchedCharacters = MutableLiveData<Characters>()
    val searchedCharacters: LiveData<Characters>
        get() = _searchedCharacters

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getCharacters() {
        compositeDisposable.add(
            catalogRepository.requestCharacters()
                .subscribe({
                    //add data to a liveData
                    _characters.postValue(it)
                },
                    { error ->
                        _errorMessage.postValue(error.message)
                    })
        )
    }

    fun getSearchedCharacters(nameToSearch: String) {
        viewModelScope.launch {
            val foundCharacters = catalogRepository.getSearchedCharacters(nameToSearch)
            _searchedCharacters.postValue(foundCharacters)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}