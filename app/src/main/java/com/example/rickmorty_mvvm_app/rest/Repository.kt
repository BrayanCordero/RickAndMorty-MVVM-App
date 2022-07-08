package com.example.rickmorty_mvvm_app.rest

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickmorty_mvvm_app.models.character.Character
import com.example.rickmorty_mvvm_app.models.character.CharacterResponse
import com.example.rickmorty_mvvm_app.utils.CharactersPaging
import com.example.rickmorty_mvvm_app.utils.UIState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


interface RickAndMortyRepository{
    suspend fun getAllCharacters(page:Int) : Response<CharacterResponse>
//    fun getPagingCharacters(): Flow<PagingData<UIState>>
}

class RepositoryImp @Inject constructor(
    private val serviceApi : ApiService
): RickAndMortyRepository {

    override suspend fun getAllCharacters(page:Int): Response<CharacterResponse> =
        serviceApi.getAllCharacters(page)

//    override fun getPagingCharacters(): Flow<PagingData<UIState>> =
//        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
//        pagingSourceFactory = {
//            CharactersPaging(serviceApi)
//        }).flow
//
}