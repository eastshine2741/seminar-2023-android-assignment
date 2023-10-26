package com.jutak.assignment3.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun launchSuspendApi(
    context: Context,
    api: suspend () -> Unit,
) {
    try {
        withContext(Dispatchers.IO) {
            api()
        }
    } catch(e: Exception) {
        e.printStackTrace()
    }
}