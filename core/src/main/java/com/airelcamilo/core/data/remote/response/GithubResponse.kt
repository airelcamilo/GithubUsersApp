package com.airelcamilo.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
    @field:SerializedName("items")
    val items: List<UserItem>
)

data class UserItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)