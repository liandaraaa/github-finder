package com.lianda.githubfinder.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianda.githubfinder.domain.model.EndlessUser
import com.lianda.githubfinder.domain.repository.UserRepository
import com.lianda.githubfinder.domain.usecase.UserUseCase
import com.lianda.githubfinder.utils.common.ResultState
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository):ViewModel() , UserUseCase{

    private val getUsers = MutableLiveData<ResultState<EndlessUser>>()

    init {
        getUsers.value = ResultState.Loading()
    }

    override fun getUsers(query: String, page: Int): LiveData<ResultState<EndlessUser>> {
        viewModelScope.launch {
            val userResponse = repository.getUsers(query, page)
            getUsers.value = userResponse
        }
        return getUsers
    }

}