package com.jutak.assignment3.network

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun launchNetworkApi(
    context: Context,
    api: suspend () -> Unit,
) {
    try {
        withContext(Dispatchers.IO) {
            api()
        }
    } catch(e: Exception) {
        if (e is HttpException) {
            Toast.makeText(context, "HTTP ${e.code()}: ${e.message()}", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "알 수 없는 에러입니다.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}