package com.example.rickmorty_mvvm_app.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorty_mvvm_app.domain.mapToDomainCharacter
import com.example.rickmorty_mvvm_app.domain.mapToDomainLocation
import com.example.rickmorty_mvvm_app.models.character.Character
import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import com.example.rickmorty_mvvm_app.rest.RickAndMortyRepository
import com.example.rickmorty_mvvm_app.utils.CharactersPaging
import com.example.rickmorty_mvvm_app.utils.FailureResponseException
import com.example.rickmorty_mvvm_app.utils.ResponseBodyNullException
import com.example.rickmorty_mvvm_app.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
): ViewModel() {

//    var recyclerChildCount: Int = 0
    var recyclerState: Parcelable? = null
    private val _characters: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val characters: LiveData<UIState> get() = _characters

    private val _locations: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val locations: LiveData<UIState> get() = _locations

//    private val _charactersPaging: MutableLiveData<PagingData<UIState>> = MutableLiveData()
//    val charactersPaging: LiveData<PagingData<UIState>> get() = _charactersPaging

    fun getAllCharacters(page:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = rickAndMortyRepository.getAllCharacters(page)
                if(response.isSuccessful){
                    response.body()?.let {
                        withContext(Dispatchers.Main){
//                            _characters.value= UIState.SUCCESS(it)
                            _characters.value = UIState.SUCCESS(it.results.mapToDomainCharacter())
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

    fun getAllLocations(page: Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = rickAndMortyRepository.getAllLocations(page)
                if(response.isSuccessful){
                    response.body()?.let {
                        withContext(Dispatchers.Main){
                            _locations.value = UIState.SUCCESS(it.results.mapToDomainLocation())
                        }
                    }?: throw ResponseBodyNullException(ERROR_GETTING_LOCATIONS)
                }else{
                    throw FailureResponseException(ERROR_GETTING_LOCATIONS)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _locations.postValue(UIState.ERROR(e))
                }
            }
        }
    }

//    fun getPagingCharacters(){
//        CoroutineScope(Dispatchers.IO).launch {
//           rickAndMortyRepository.getPagingCharacters().collect{
//               _charactersPaging.postValue(it)
//           }
//        }
//    }



        companion object {
        private const val ERROR_GETTING_CHARACTERS = "CHARACTERS"
        private const val ERROR_GETTING_LOCATIONS = "LOCATIONS"

    }
}

