package com.lianda.githubfinder.domain.repository

import com.lianda.githubfinder.domain.model.EndlessUser
import com.lianda.githubfinder.utils.common.ResultState


interface UserRepository {
    suspend fun getUsers(query:String,page:Int):ResultState<EndlessUser>
}