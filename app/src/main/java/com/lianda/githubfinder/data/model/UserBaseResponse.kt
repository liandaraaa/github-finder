package com.lianda.githubfinder.data.model


import com.google.gson.annotations.SerializedName

data class UserBaseResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val userItemResponses: List<UserItemResponse>?,
    @SerializedName("total_count")
    val totalCount: Int?
)