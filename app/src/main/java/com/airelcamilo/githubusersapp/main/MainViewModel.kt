package com.airelcamilo.githubusersapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.domain.usecase.UserUseCase
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.utils.DataMapper
import kotlinx.coroutines.flow.map

class MainViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _listUsers = MutableLiveData<Resource<List<UserModel>>>()
    val listUsers: LiveData<Resource<List<UserModel>>> = _listUsers

    init {
        loadUsers()
    }

    private fun loadUsers() {
        userUseCase.getAllUsers()
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Success -> Resource.Success(DataMapper.mapUserDomainsToModel(resource.data ?: emptyList()))
                    is Resource.Error -> Resource.Error(resource.message ?: "")
                }
            }
            .asLiveData()
            .observeForever { resource ->
                _listUsers.postValue(resource)
            }
    }

    fun search(q: String) {
        userUseCase.search(q)
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Success -> Resource.Success(DataMapper.mapUserDomainsToModel(resource.data ?: emptyList()))
                    is Resource.Error -> Resource.Error(resource.message ?: "")
                }
            }
            .asLiveData()
            .observeForever { resource ->
                _listUsers.postValue(resource)
            }
    }
}