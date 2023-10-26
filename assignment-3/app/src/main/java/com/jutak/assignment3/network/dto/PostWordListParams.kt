package com.jutak.assignment3.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostWordListParams(
    val name: String,
    val owner: String,
    val password: String,
)