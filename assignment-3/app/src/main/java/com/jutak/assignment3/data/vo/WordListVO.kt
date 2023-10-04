package com.jutak.assignment3.data.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WordListVO(
    val id: Int,
    val name: String,
    val owner: String,
    @Json(name = "word_list") val wordList: List<WordVO>
)