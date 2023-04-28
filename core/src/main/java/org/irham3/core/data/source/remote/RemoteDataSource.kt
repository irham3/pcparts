package org.irham3.core.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.irham3.core.data.source.remote.network.ApiResponse
import org.irham3.core.data.source.remote.network.ApiService
import org.irham3.core.data.source.remote.response.ComponentResponse

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllComponent(): Flow<ApiResponse<List<ComponentResponse>>> {
        return flow {
            try {
                val response = apiService.getComponents()
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}