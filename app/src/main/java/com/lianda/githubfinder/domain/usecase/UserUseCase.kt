package com.lianda.githubfinder.domain.usecase

import androidx.lifecycle.LiveData
import com.lianda.githubfinder.domain.model.EndlessUser
import com.lianda.githubfinder.utils.common.ResultState

interface UserUseCase {
    fun getUsers(query:String,page:Int):LiveData<ResultState<EndlessUser>>
}