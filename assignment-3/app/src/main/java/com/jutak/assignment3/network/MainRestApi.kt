package com.jutak.assignment3.network

import com.jutak.assignment3.network.dto.GetWordListResponse
import com.jutak.assignment3.network.dto.GetWordListsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRestApi {
    @GET("/myapp/v1/word_lists")
    suspend fun _getWordLists(): GetWordListsResponse

    @GET("/myapp/v1/word_list/{id}")
    suspend fun _getWordListDetail(
        @Path("id") id: Int
    ): GetWordListResponse
}