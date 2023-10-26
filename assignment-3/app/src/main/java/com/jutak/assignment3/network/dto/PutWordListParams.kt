package com.jutak.assignment3.network.dto

import com.jutak.assignment3.data.vo.WordVO
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PutWordListParams (
    val password: String,
    val word: WordVO,
)