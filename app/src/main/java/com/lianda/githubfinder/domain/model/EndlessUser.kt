package com.lianda.githubfinder.domain.model

data class EndlessUser(
    var totalPage:Int = 0,
    var user:List<User> = listOf()
)