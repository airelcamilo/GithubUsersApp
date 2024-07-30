package com.airelcamilo.core.domain.usecase

import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.domain.model.User
import com.airelcamilo.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getFavoriteUserByUsername(username: String): Flow<User>
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun search(q: String): Flow<Resource<List<User>>>
    fun getUserDetail(username: String): Flow<Resource<UserDetail>>
    fun getListUserFollowers(username: String): Flow<Resource<List<User>>>
    fun getListUserFollowing(username: String): Flow<Resource<List<User>>>
    fun getAllFavorites(): Flow<List<User>>
    fun setFavorite(user: User, state: Boolean)
}