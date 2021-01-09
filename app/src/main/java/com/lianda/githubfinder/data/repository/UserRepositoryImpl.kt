package com.lianda.githubfinder.data.repository

import com.lianda.githubfinder.data.remote.UserApiClient
import com.lianda.githubfinder.domain.model.EndlessUser
import com.lianda.githubfinder.domain.repository.UserRepository
import com.lianda.githubfinder.utils.common.ResultState
import com.lianda.githubfinder.utils.extentions.handleApiError
import com.lianda.githubfinder.utils.extentions.handleApiSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl (private val userApi: UserApiClient):UserRepository{
    override suspend fun getUsers(query: String,page:Int): ResultState<EndlessUser> {
        return try {
            val response = userApi.getUsers(query = query,page = page)
            if (response.isSuccessful){
                response.body()?.let {userResponse->
                    withContext(Dispatchers.IO){
                        val endlessUser = EndlessUser(
                            totalPage = userResponse.totalCount ?: 0,
                            user =  userResponse.userItemResponses?.map {it.toUser()}.orEmpty()
                        )
                        handleApiSuccess(data = endlessUser)
                    }
                } ?: handleApiError(response)
            }else{
                handleApiError(response)
            }
        }catch (e: Exception){
            ResultState.Error(e.message ?: "Terjadi Kesalahan")
        }
    }
}