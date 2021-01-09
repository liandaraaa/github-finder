package com.lianda.githubfinder.utils.common

sealed class ResultState<out T> {
    class Loading<T> : ResultState<T>()
    class Empty<T> : ResultState<T>()
    class Success<T>(val data: T) : ResultState<T>()
    class Error(val message: String) : ResultState<Nothing>()
}