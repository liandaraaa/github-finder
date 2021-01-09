package com.lianda.githubfinder.utils.common

import android.util.Log
import com.google.gson.GsonBuilder
import com.lianda.githubfinder.data.model.ErrorResponse
import retrofit2.Response
import java.io.IOException

object ApiErrorOperator {

    fun parseError(response:Response<*>?): ErrorResponse {
        val gson = GsonBuilder().create()
        val error: ErrorResponse

        try {
            error = gson.fromJson(response?.errorBody()?.string(), ErrorResponse::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("error", it) }
            return ErrorResponse(e.message ?: "Terjadi Kesalahan")
        }
        return error
    }

}