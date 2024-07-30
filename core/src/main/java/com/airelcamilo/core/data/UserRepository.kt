@file:Suppress("SameReturnValue")

package com.airelcamilo.core.data

import com.airelcamilo.core.data.local.LocalDataSource
import com.airelcamilo.core.data.remote.RemoteDataSource
import com.airelcamilo.core.data.remote.network.ApiResponse
import com.airelcamilo.core.data.remote.response.UserDetailResponse
import com.airelcamilo.core.data.remote.response.UserItem
import com.airelcamilo.core.domain.model.User
import com.airelcamilo.core.domain.model.UserDetail
import com.airelcamilo.core.domain.repository.IUserRepository
import com.airelcamilo.core.utils.AppExecutors
import com.airelcamilo.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Suppress("SameReturnValue")
class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IUserRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavoriteUserByUsername(username: String): Flow<User> {
        return localDataSource.getFavoriteUserByUsername(username).flatMapConcat { userEntity ->
            if (userEntity == null) {
                flowOf(User(login = "", avatarUrl = "", isFavorite = false))
            } else {
                flowOf(
                    User(
                        login = userEntity.login,
                        avatarUrl = userEntity.avatarUrl,
                        isFavorite = userEntity.isFavorite
                    )
                )
            }
        }.distinctUntilChanged()
    }

    override fun getAllUsers(): Flow<Resource<List<User>>> =
        object : RemoteBoundResource<List<User>, List<UserItem>>() {
            override suspend fun transformApiResponse(data: List<UserItem>): List<User> {
                return DataMapper.mapUserEntitiesToDomain(DataMapper.mapUserResponsesToEntities(data))
            }

            override fun onEmpty(): List<UserItem> {
                return emptyList()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getListUsers()
        }.asFlow()

    override fun search(q: String): Flow<Resource<List<User>>> =
        object : RemoteBoundResource<List<User>, List<UserItem>>() {
            override suspend fun transformApiResponse(data: List<UserItem>): List<User> {
                return DataMapper.mapUserEntitiesToDomain(DataMapper.mapUserResponsesToEntities(data))
            }

            override fun onEmpty(): List<UserItem> {
                return emptyList()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.search(q)
        }.asFlow()

    override fun getUserDetail(username: String): Flow<Resource<UserDetail>> =
        object : RemoteBoundResource<UserDetail, UserDetailResponse>() {
            override suspend fun transformApiResponse(data: UserDetailResponse): UserDetail {
                return DataMapper.mapUserDetailResponseToDomain(data)
            }

            override fun onEmpty(): UserDetailResponse {
                return UserDetailResponse()
            }

            override suspend fun createCall(): Flow<ApiResponse<UserDetailResponse>> =
                remoteDataSource.getUserDetail(username)
        }.asFlow()

    override fun getListUserFollowers(username: String): Flow<Resource<List<User>>> =
        object : RemoteBoundResource<List<User>, List<UserItem>>() {
            override suspend fun transformApiResponse(data: List<UserItem>): List<User> {
                return DataMapper.mapUserEntitiesToDomain(DataMapper.mapUserResponsesToEntities(data))
            }

            override fun onEmpty(): List<UserItem> {
                return emptyList()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getListUserFollowers(username)
        }.asFlow()

    override fun getListUserFollowing(username: String): Flow<Resource<List<User>>> =
        object : RemoteBoundResource<List<User>, List<UserItem>>() {
            override suspend fun transformApiResponse(data: List<UserItem>): List<User> {
                return DataMapper.mapUserEntitiesToDomain(DataMapper.mapUserResponsesToEntities(data))
            }

            override fun onEmpty(): List<UserItem> {
                return emptyList()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getListUserFollowing(username)
        }.asFlow()

    override fun getAllFavorites(): Flow<List<User>> {
        return localDataSource.getAllFavorites().map {
            DataMapper.mapUserEntitiesToDomain(it)
        }
    }

    override fun setFavorite(user: User, state: Boolean) {
        val userEntity = DataMapper.mapUserDomainToEntity(user)
        CoroutineScope(Dispatchers.IO).launch {
            val existingUser = localDataSource.getFavoriteUserByUsername(userEntity.login).first()

            if (existingUser == null) {
                localDataSource.insertUsers(listOf(userEntity))
            } else {
                appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userEntity, state) }
            }
        }
    }
}