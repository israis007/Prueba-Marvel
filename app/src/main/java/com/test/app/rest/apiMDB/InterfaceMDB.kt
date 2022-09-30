package com.test.app.rest.apiMDB

import com.test.app.BuildConfig
import com.test.app.rest.responses.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Local Vars
const val ENDPOINT = "${BuildConfig.BASE_URL}movie"
const val KEY = BuildConfig.MDB_KEY

//Query fields
const val API_KEY = "api_key"
const val LANGUAGE = "language"
const val PAGE = "page"

interface InterfaceMDB {


    @GET("$ENDPOINT/popular")
    suspend fun getPopularMovies(@Query(API_KEY) key: String = KEY, @Query(LANGUAGE) language: String, @Query(PAGE) page: Int) : Response<PopularMoviesResponse>


}