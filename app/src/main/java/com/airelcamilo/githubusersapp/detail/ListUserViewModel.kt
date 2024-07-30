package com.airelcamilo.githubusersapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.domain.model.User
import com.airelcamilo.core.domain.usecase.UserUseCase
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListUserViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getUsers(tab: Int, username: String): LiveData<Resource<List<UserModel>>> {
        val source: Flow<Resource<List<User>>> = when (tab) {
            1 -> userUseCase.getListUserFollowers(username)
            else -> userUseCase.getListUserFollowing(username)
        }

        return source.map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> Resource.Success(DataMapper.mapUserDomainsToModel(resource.data ?: emptyList()))
                is Resource.Error -> Resource.Error(resource.message ?: "")
            }
        }.asLiveData()
    }
}