package com.airelcamilo.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.airelcamilo.core.domain.usecase.UserUseCase
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.utils.DataMapper
import kotlinx.coroutines.flow.map

class FavoriteViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getAllFavorites(): LiveData<List<UserModel>> {
        return userUseCase.getAllFavorites().map { users ->
            DataMapper.mapUserDomainsToModel(users)
        }.asLiveData()
    }
}