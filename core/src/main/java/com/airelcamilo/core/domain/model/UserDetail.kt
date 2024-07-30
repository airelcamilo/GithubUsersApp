package com.airelcamilo.core.domain.model

data class UserDetail(
    val login: String = "",
    val followers: Int = 0,
    val avatarUrl: String = "",
    val following: Int = 0,
    val name: String = "",
    val htmlUrl: String = ""
)
