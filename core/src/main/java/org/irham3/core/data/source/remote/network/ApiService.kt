package org.irham3.core.data.source.remote.network

import org.irham3.core.BuildConfig
import org.irham3.core.data.source.remote.response.ComponentResponse
import retrofit2.http.GET

interface ApiService {
    @GET("components?apikey=${BuildConfig.BASE_API_KEY}")
    suspend fun getComponents(): List<ComponentResponse>
}