package org.irham3.core.di

import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("irham3".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            ComponentDatabase::class.java, "Component.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "onawoodgnwkncueeyusr.supabase.co"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/RoGpx/qhCo85TFWNaHJk4hSNoYlYDanumKYpwL/jUN0=")
            .add(hostname, "sha256/RYg5zREI094DHWadRQaQEbCby4m3dS4kH3YC01VPE14=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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