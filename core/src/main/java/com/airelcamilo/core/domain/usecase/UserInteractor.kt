package com.airelcamilo.core.domain.usecase

import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.domain.model.User
import com.airelcamilo.core.domain.model.UserDetail
import com.airelcamilo.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun getFavoriteUserByUsername(username: String): Flow<User> = userRepository.getFavoriteUserByUsername(username)
    override fun getAllUsers(): Flow<Resource<List<User>>> = userRepository.getAllUsers()
    override fun search(q: String): Flow<Resource<List<User>>> = userRepository.search(q)
    override fun getUserDetail(username: String): Flow<Resource<UserDetail>> = userRepository.getUserDetail(username)
    override fun getListUserFollowers(username: String): Flow<Resource<List<User>>> = userRepository.getListUserFollowers(username)
    override fun getListUserFollowing(username: String): Flow<Resource<List<User>>> = userRepository.getListUserFollowing(username)
    override fun getAllFavorites(): Flow<List<User>> = userRepository.getAllFavorites()
    override fun setFavorite(user: User, state: Boolean) = userRepository.setFavorite(user, state)
}