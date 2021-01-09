package com.lianda.githubfinder.utils.di

import com.lianda.githubfinder.data.remote.UserApiClient
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideUserApi(get()) }
}

fun provideUserApi(retrofit: Retrofit): UserApiClient= retrofit.create(UserApiClient::class.java)