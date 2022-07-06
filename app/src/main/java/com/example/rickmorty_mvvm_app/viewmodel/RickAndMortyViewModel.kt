package com.example.rickmorty_mvvm_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickmorty_mvvm_app.domain.mapToDomainCharacter
import com.example.rickmorty_mvvm_app.rest.RickAndMortyRepository
import com.example.rickmorty_mvvm_app.utils.FailureResponseException
import com.example.rickmorty_mvvm_app.utils.ResponseBodyNullException
import com.example.rickmorty_mvvm_app.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
): ViewModel() {

    private val _characters: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val characters: LiveData<UIState> get() = _characters

    fun getAllCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = rickAndMortyRepository.getAllCharacters()
                if(response.isSuccessful){
                    response.body()?.let {
                        withContext(Dispatchers.Main){
                            _characters.value= UIState.SUCCESS(it)
//                            _characters.value = UIState.SUCCESS(it.results.mapToDomainCharacter())
                        }
                    }?: throw ResponseBodyNullException(ERROR_GETTING_CHARACTERS)
                }else{
                    throw FailureResponseException(ERROR_GETTING_CHARACTERS)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _characters.postValue(UIState.ERROR(e))
                }
            }
        }
    }





        companion object {
        private const val ERROR_GETTING_CHARACTERS = "CHARACTERS"

    }
}

