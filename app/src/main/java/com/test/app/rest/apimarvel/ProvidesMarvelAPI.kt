package com.test.app.rest.apimarvel

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.app.BuildConfig
import com.test.app.rest.interceptors.MarvelHeaderInterceptor
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


const val CLIENT_MARVEL     = "clientMarvel"
const val RETROFIT_MARVEL   = "retrofitMarvel"


@Module
@InstallIn(SingletonComponent::class)
class ProvidesMarvelAPI @Inject constructor() {

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
    @Named(CLIENT_MARVEL)
    fun clientMarvel(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(NetworkAvailableInterceptor())
            .addInterceptor(MarvelHeaderInterceptor.createMarvelHeaderInterceptor())
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
    @Named(RETROFIT_MARVEL)
    fun provideRetrofitMarvel(@Named(CLIENT_MARVEL) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(gsonConverterFactory)
        }.build()

    @Provides
    @Singleton
    fun marvelAPI(@Named (RETROFIT_MARVEL) retrofit: Retrofit): InterfaceMarvel =
        retrofit.create(InterfaceMarvel::class.java)

}