package com.lianda.githubfinder.utils.extentions

import com.lianda.githubfinder.utils.common.ApiErrorOperator
import com.lianda.githubfinder.utils.common.ResultState
import retrofit2.Response

fun <T : Any> handleApiSuccess(data: T): ResultState<T> {
    val isList = data is List<*>
    return if (isList) {
        return if ((data as List<*>).isEmpty()) {
            ResultState.Empty()
        } else {
            ResultState.Success(data)
        }
    } else {
        ResultState.Success(data)
    }
}

fun <T : Any> handleApiError(response: Response<T>): ResultState.Error {
    val error = ApiErrorOperator.parseError(response)
    return ResultState.Error(error.message)
}

