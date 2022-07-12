package com.example.rickmorty_mvvm_app.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickmorty_mvvm_app.domain.mapToDomainCharacter
import com.example.rickmorty_mvvm_app.domain.mapToDomainLocation
import com.example.rickmorty_mvvm_app.rest.RickAndMortyRepository
import com.example.rickmorty_mvvm_app.utils.FailureResponseException
import com.example.rickmorty_mvvm_app.utils.ResponseBodyNullException
import com.example.rickmorty_mvvm_app.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    var recyclerState: Parcelable? = null


    private val _characters: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val characters: LiveData<UIState> get() = _characters

    private val _locations: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val locations: LiveData<UIState> get() = _locations

//    private val _charactersPaging: MutableLiveData<PagingData<UIState>> = MutableLiveData()
//    val charactersPaging: LiveData<PagingData<UIState>> get() = _charactersPaging

    fun getAllCharacters(page:Int) {
        CoroutineScope(ioDispatcher).launch {
            try {
                val response = rickAndMortyRepository.getAllCharacters(page)
                if(response.isSuccessful){
                    response.body()?.let {
                        withContext(Dispatchers.Main){
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
        CoroutineScope(ioDispatcher).launch {
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

