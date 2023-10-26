package com.jutak.assignment3.network

import com.jutak.assignment3.network.dto.GetWordListResponse
import com.jutak.assignment3.network.dto.GetWordListsResponse
import com.jutak.assignment3.network.dto.PostWordListParams
import com.jutak.assignment3.network.dto.PostWordListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainRestApi {
    @GET("/myapp/v1/word_lists")
    suspend fun _getWordLists(): GetWordListsResponse

    @GET("/myapp/v1/word_list/{id}")
    suspend fun _getWordListDetail(
        @Path("id") id: Int
    ): GetWordListResponse

    @POST("/myapp/v1/word_list")
    suspend fun _postWordList(
        @Body body: PostWordListParams
    ): PostWordListResponse
}