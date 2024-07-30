package com.airelcamilo.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.airelcamilo.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity WHERE isFavorite = 1 AND login = :username")
    fun getFavoriteUserByUsername(username: String): Flow<UserEntity?>

    @Query("SELECT * from UserEntity")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * from UserEntity WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<UserEntity>>

    @Update
    fun updateFavoriteUser(user: UserEntity)
}