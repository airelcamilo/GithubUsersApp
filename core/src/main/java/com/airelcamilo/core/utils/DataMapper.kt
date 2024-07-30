package com.airelcamilo.core.utils

import com.airelcamilo.core.data.local.entity.UserEntity
import com.airelcamilo.core.data.remote.response.UserDetailResponse
import com.airelcamilo.core.data.remote.response.UserItem
import com.airelcamilo.core.domain.model.User
import com.airelcamilo.core.domain.model.UserDetail
import com.airelcamilo.core.presentation.model.UserDetailModel
import com.airelcamilo.core.presentation.model.UserModel

object DataMapper {
    fun mapUserResponsesToEntities(input: List<UserItem>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        input.map {
            val favorite = UserEntity(
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = false
            )
            userList.add(favorite)
        }
        return userList
    }

    fun mapUserEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapUserDomainsToModel(input: List<User>): List<UserModel> =
        input.map {
            UserModel(
                login = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapUserDomainToModel(input: User): UserModel =
        UserModel(
            login = input.login,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )

    fun mapUserModelToDomain(input: UserModel) = User(
        login = input.login,
        avatarUrl = input.avatarUrl,
        isFavorite = input.isFavorite
    )

    fun mapUserDomainToEntity(input: User) = UserEntity(
        login = input.login,
        avatarUrl = input.avatarUrl,
        isFavorite = input.isFavorite
    )

    fun mapUserDetailResponseToDomain(input: UserDetailResponse) = UserDetail(
        login = input.login ?: "",
        followers = input.followers ?: 0,
        avatarUrl = input.avatarUrl ?: "",
        following = input.following ?: 0,
        name = input.name ?: "",
        htmlUrl = input.name ?: ""
    )

    fun mapUserDetailDomainToModel(input: UserDetail) = UserDetailModel(
        login = input.login,
        followers = input.followers,
        avatarUrl = input.avatarUrl,
        following = input.following,
        name = input.name,
        htmlUrl = input.name
    )
}