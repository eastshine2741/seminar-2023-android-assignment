package com.jutak.assignment3.data.vo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WordVO(
    val spell: String,
    val meaning: String,
    val synonym: String?,
    val antonym: String?,
    val sentence: String?
)