package com.test.app.rest.interceptors


import com.test.app.AppTest
import okhttp3.Interceptor


private const val TIME = "Date"
private const val CONTENT = "Content-Type"
private const val CONTENT_TYPE = "application/json; charset=utf-8"

class MarvelHeaderInterceptor  {

    companion object {
        fun createMarvelHeaderInterceptor() : Interceptor {
            return Interceptor { chain ->
                val request = chain.request().newBuilder()

                request.addHeader(CONTENT, CONTENT_TYPE)
                request.addHeader(TIME, AppTest.instance.getCurrentDateFormatted())

                chain.proceed(request.build())
            }
        }
    }
}