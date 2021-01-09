package com.lianda.githubfinder.data.remote

import com.lianda.githubfinder.data.model.UserBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiClient {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") query: String,
        @Query("per_page") limit: Int = 10,
        @Query("page") page: Int
    ): Response<UserBaseResponse>
}