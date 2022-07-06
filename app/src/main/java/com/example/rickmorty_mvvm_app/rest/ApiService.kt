package com.example.rickmorty_mvvm_app.rest

import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(CHARACTER_PATH)
    suspend fun getAllCharacters(): Response<CharacterResponse>



    companion object{
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        private const val CHARACTER_PATH = "character"
    }
}