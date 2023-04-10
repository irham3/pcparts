package org.irham3.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.irham3.core.data.source.remote.network.ApiResponse
import org.irham3.core.data.source.remote.network.ApiService
import org.irham3.core.data.source.remote.response.ComponentResponse

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllPart(): Flow<ApiResponse<List<ComponentResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.response
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.response))
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