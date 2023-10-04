package com.jutak.assignment3.network

import com.jutak.assignment3.network.dto.GetWordListResponse
import com.jutak.assignment3.network.dto.GetWordListsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRestApi {
    @GET("/word_lists")
    fun _getWordLists(): GetWordListsResponse

    @GET("/word_list/{id}")
    fun _getWordList(
        @Path("id") id: Int
    ): GetWordListResponse
}