package org.irham3.core.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.irham3.core.BuildConfig
import org.irham3.core.data.ComponentRepository
import org.irham3.core.data.source.local.LocalDataSource
import org.irham3.core.data.source.local.room.ComponentDatabase
import org.irham3.core.data.source.remote.RemoteDataSource
import org.irham3.core.data.source.remote.network.ApiService
import org.irham3.core.domain.repository.IComponentRepository
import org.irham3.core.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ComponentDatabase>().componentDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            ComponentDatabase::class.java, "Component.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IComponentRepository> {
        ComponentRepository(
            get(),
            get(),
            get()
        )
    }
}