package com.airelcamilo.core.data.remote.network

import com.airelcamilo.core.data.remote.response.GithubResponse
import com.airelcamilo.core.data.remote.response.UserDetailResponse
import com.airelcamilo.core.data.remote.response.UserItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getListUsers(
        @Query("q") q: String
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getListUserFollowers(
        @Path("username") username: String
    ): List<UserItem>

    @GET("users/{username}/following")
    suspend fun getListUserFollowing(
        @Path("username") username: String
    ): List<UserItem>
}