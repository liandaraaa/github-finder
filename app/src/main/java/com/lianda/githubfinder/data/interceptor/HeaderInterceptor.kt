package com.lianda.githubfinder.data.interceptor

import com.lianda.githubfinder.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
                .addHeader("accept", "application/vnd.github.v3+json")
                .addHeader("Authorization", "token ${BuildConfig.ACCESS_TOKEN}")
        return requestBuilder.build()
    }


}