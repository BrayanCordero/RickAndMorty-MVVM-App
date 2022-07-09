package com.example.rickmorty_mvvm_app.rest

import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import com.example.rickmorty_mvvm_app.models.location.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(CHARACTER_PATH)
    suspend fun getAllCharacters(
        @Query("page") page : Int
    ): Response<CharacterResponse>

    @GET(LOCATION_PATH)
    suspend fun getAllLocations(
        @Query("page") page: Int
    ): Response<LocationResponse>



    companion object{
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        private const val CHARACTER_PATH = "character"
        private const val LOCATION_PATH = "location"
    }
}