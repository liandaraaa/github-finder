package com.lianda.githubfinder.utils.di.featuremodule

import com.lianda.githubfinder.data.repository.UserRepositoryImpl
import com.lianda.githubfinder.domain.repository.UserRepository
import com.lianda.githubfinder.domain.usecase.UserUseCase
import com.lianda.githubfinder.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {

    single<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }

    single<UserUseCase>{get()}

    viewModel { UserViewModel(get()) }
}