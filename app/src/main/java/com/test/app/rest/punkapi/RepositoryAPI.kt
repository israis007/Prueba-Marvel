package com.test.app.rest.punkapi

import androidx.lifecycle.ViewModelStoreOwner
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.app.AppTest
import com.test.app.BuildConfig
import com.test.app.R
import com.test.app.rest.interceptors.NetworkAvailableInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RepositoryAPI {


    companion object {

        private val TAG = "RepositoryAPI"

        private fun httpInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        private var service: InterfacePunkAPI? = null

        private fun getService(viewModel: ViewModelStoreOwner): InterfacePunkAPI =
            service ?: synchronized(this) {
                val httpCLient = OkHttpClient.Builder()
                    .addInterceptor(httpInterceptor())
                    .addInterceptor(NetworkAvailableInterceptor())
                    .callTimeout(
                        AppTest.instance.resources.getInteger(R.integer.timeOut).toLong(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        AppTest.instance.resources.getInteger(R.integer.timeOut).toLong(),
                        TimeUnit.SECONDS
                    )
                    .connectTimeout(
                        AppTest.instance.resources.getInteger(R.integer.timeOut).toLong(),
                        TimeUnit.SECONDS
                    )
                    .build()
                val builder = Retrofit.Builder()
                builder.client(httpCLient)
                builder.baseUrl(BuildConfig.BASE_URL)
                builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
                builder.addConverterFactory(GsonConverterFactory.create())

                service ?: builder.build().create(InterfacePunkAPI::class.java)
            }


    }
}