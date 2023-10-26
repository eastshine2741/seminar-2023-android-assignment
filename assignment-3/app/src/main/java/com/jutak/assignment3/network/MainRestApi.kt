package com.jutak.assignment3.network

import com.jutak.assignment3.network.dto.DeleteWordListParams
import com.jutak.assignment3.network.dto.GetWordListResponse
import com.jutak.assignment3.network.dto.GetWordListsResponse
import com.jutak.assignment3.network.dto.PostHasPermissionParams
import com.jutak.assignment3.network.dto.PostHasPermissionResponse
import com.jutak.assignment3.network.dto.PostWordListParams
import com.jutak.assignment3.network.dto.PostWordListResponse
import com.jutak.assignment3.network.dto.PutWordListParams
import com.jutak.assignment3.network.dto.PutWordListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("/myapp/v1/word_list/{id}/permission")
    suspend fun _postHasPermission(
        @Path("id") id: Int,
        @Body password: PostHasPermissionParams,
    ): PostHasPermissionResponse

    @HTTP(method = "DELETE", path = "/myapp/v1/word_list/{id}", hasBody = true)
    suspend fun _deleteWordList(
        @Path("id") id: Int,
        @Body password: DeleteWordListParams,
    )

    @PUT("/myapp/v1/word_list/{id}")
    suspend fun _putWordList(
        @Path("id") id: Int,
        @Body body: PutWordListParams,
    ): PutWordListResponse
}