package com.jutak.assignment3.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteWordListParams(
    val password: String,
)