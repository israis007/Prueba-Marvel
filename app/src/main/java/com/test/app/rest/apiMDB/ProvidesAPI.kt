package com.test.app.rest.apiMDB

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.app.BuildConfig
import com.test.app.rest.interceptors.NetworkAvailableInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


const val CLIENT_RETROFIT       = "clientMDB"
const val RETROFIT_MDB          = "retrofitMDB"


@Module
@InstallIn(SingletonComponent::class)
class ProvidesAPI @Inject constructor() {

    private val gsonConverterFactory by lazy {
        GsonConverterFactory.create(
            Gson().newBuilder()
                .serializeNulls()
                .setDateFormat(
                    BuildConfig.DATE_FORMAT
                ).create()
        )
    }

    private val logInterceptor: Interceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named(CLIENT_RETROFIT)
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(NetworkAvailableInterceptor())
            .callTimeout(
                BuildConfig.TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            )
            .readTimeout(
                BuildConfig.TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            )
            .connectTimeout(
                BuildConfig.TIMEOUT_SECONDS,
                TimeUnit.SECONDS
            )
            .build()

    @Provides
    @Singleton
    @Named(RETROFIT_MDB)
    fun retrofit(@Named(CLIENT_RETROFIT) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(gsonConverterFactory)
        }.build()

    @Provides
    @Singleton
    fun mbdAPI(@Named (RETROFIT_MDB) retrofit: Retrofit): InterfaceMDB =
        retrofit.create(InterfaceMDB::class.java)

}