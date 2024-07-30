package com.airelcamilo.core.data.remote

import android.util.Log
import com.airelcamilo.core.data.remote.network.ApiResponse
import com.airelcamilo.core.data.remote.network.ApiService
import com.airelcamilo.core.data.remote.response.UserDetailResponse
import com.airelcamilo.core.data.remote.response.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getListUsers(): Flow<ApiResponse<List<UserItem>>> {
        return flow {
            try {
                val response = apiService.getListUsers("a")
                val dataArray = response.items
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun search(q: String): Flow<ApiResponse<List<UserItem>>> {
        return flow {
            try {
                val response = apiService.getListUsers(q)
                val dataArray = response.items
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserDetailResponse>> {
        return flow {
            try {
                val response = apiService.getUserDetail(username)
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListUserFollowers(username: String): Flow<ApiResponse<List<UserItem>>> {
        return flow {
            try {
                val response = apiService.getListUserFollowers(username)
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListUserFollowing(username: String): Flow<ApiResponse<List<UserItem>>> {
        return flow {
            try {
                val response = apiService.getListUserFollowing(username)
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}