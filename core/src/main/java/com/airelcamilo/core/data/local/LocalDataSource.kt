package com.airelcamilo.core.data.local

import com.airelcamilo.core.data.local.entity.UserEntity
import com.airelcamilo.core.data.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {

    suspend fun insertUsers(users: List<UserEntity>) = userDao.insertUsers(users)

    fun getFavoriteUserByUsername(username: String): Flow<UserEntity?> = userDao.getFavoriteUserByUsername(username)

    fun getAllFavorites(): Flow<List<UserEntity>> = userDao.getAllFavorites()

    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()

    fun setFavoriteUser(user: UserEntity, newState: Boolean) {
        user.isFavorite = newState
        userDao.updateFavoriteUser(user)
    }
}