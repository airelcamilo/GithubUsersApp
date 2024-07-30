package com.airelcamilo.core.data

import com.airelcamilo.core.data.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class RemoteBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(transformApiResponse(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(transformApiResponse(onEmpty())))
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error(apiResponse.errorMessage)
                )
            }
        }
    }

    protected abstract fun onEmpty(): RequestType

    protected abstract suspend fun transformApiResponse(data: RequestType): ResultType

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}