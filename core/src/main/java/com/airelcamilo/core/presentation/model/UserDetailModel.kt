package com.airelcamilo.core.presentation.model

data class UserDetailModel(
    val login: String = "",
    val followers: Int = 0,
    val avatarUrl: String = "",
    val following: Int = 0,
    val name: String = "",
    val htmlUrl: String = ""
)
