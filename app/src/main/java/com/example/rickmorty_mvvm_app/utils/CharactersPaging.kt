package com.example.rickmorty_mvvm_app.utils

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty_mvvm_app.models.character.Character
import com.example.rickmorty_mvvm_app.rest.ApiService

class CharactersPaging(
    private val service: ApiService
): PagingSource<Int, Character>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getAllCharacters(pageNumber)
                val pageResponse = response.body()
                val data = pageResponse?.results
                var nextPageNumber: Int? = null
                if (pageResponse?.info?.next != null) {
                    val uri = Uri.parse(pageResponse.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextPageNumber = nextPageQuery?.toInt()
                }

                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = null,
                    nextKey = nextPageNumber
                )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

}