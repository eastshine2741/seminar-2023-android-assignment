package com.jutak.assignment3.data.vo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BriefWordListVO(
    val id: Int,
    val name: String,
    val owner: String,
)