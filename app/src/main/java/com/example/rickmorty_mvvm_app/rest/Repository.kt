package com.example.rickmorty_mvvm_app.rest

import com.example.rickmorty_mvvm_app.domain.mapToDomainCharacter
import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import com.example.rickmorty_mvvm_app.utils.FailureResponseException
import com.example.rickmorty_mvvm_app.utils.ResponseBodyNullException
import com.example.rickmorty_mvvm_app.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


interface RickAndMortyRepository{
    suspend fun getAllCharacters() : Response<CharacterResponse>
}

class RepositoryImp @Inject constructor(
    private val serviceApi : ApiService
): RickAndMortyRepository {

    override suspend fun getAllCharacters(): Response<CharacterResponse> =
        serviceApi.getAllCharacters()



//    companion object {
//        private const val ERROR_GETTING_CHARACTERS = "CHARACTERS"
//
//    }


}