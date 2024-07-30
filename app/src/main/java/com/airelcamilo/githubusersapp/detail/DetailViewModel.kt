package com.airelcamilo.githubusersapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.domain.usecase.UserUseCase
import com.airelcamilo.core.presentation.model.UserDetailModel
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.utils.DataMapper
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _userDetail = MutableLiveData<Resource<UserDetailModel>>()
    val userDetail: LiveData<Resource<UserDetailModel>> = _userDetail

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            userUseCase.getUserDetail(username).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _userDetail.postValue(Resource.Loading())
                    is Resource.Success -> {
                        resource.data?.let {
                            val userDetailModel = DataMapper.mapUserDetailDomainToModel(
                                it
                            )
                            _userDetail.postValue(Resource.Success(userDetailModel))
                        }
                    }
                    is Resource.Error -> {
                        resource.message?.let {
                            _userDetail.postValue(Resource.Error(it))
                        }
                    }
                }
            }
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<UserModel> {
        return userUseCase.getFavoriteUserByUsername(username).map { user ->
            DataMapper.mapUserDomainToModel(user)
        }.asLiveData()
    }

    fun setFavorite(user: UserModel, newStatus:Boolean) =
        userUseCase.setFavorite(DataMapper.mapUserModelToDomain(user), newStatus)
}