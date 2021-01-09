package com.lianda.githubfinder.application

import android.app.Application
import com.lianda.githubfinder.utils.di.apiModule
import com.lianda.githubfinder.utils.di.featuremodule.userModule
import com.lianda.githubfinder.utils.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubUserApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubUserApplication)
            modules(
                listOf(
                    networkModule,
                    apiModule,
                    userModule
                )
            )
        }

    }
}