package com.jutak.assignment3.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PostHasPermissionResponse (
    val valid: Boolean
)