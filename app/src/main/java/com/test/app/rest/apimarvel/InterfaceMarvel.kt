package com.test.app.rest.apimarvel

import com.test.app.rest.responses.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/* End point */
private const val ENDPOINT      = "/v1/public"

/* Constants Queries */
private const val ORDER_BY      = "orderBy"
private const val LIMIT         = "limit"
private const val TIMESTAMP     = "ts"
private const val API_KEY       = "apikey"
private const val HASH          = "hash"

interface InterfaceMarvel {


    @GET("$ENDPOINT/characters")
    suspend fun getCharacters(
        @Query(ORDER_BY)    orderBy     : String    = "name",
        @Query(LIMIT)       limit       : Int       = 50,
        @Query(TIMESTAMP)   timeStamp   : Long      = 1,
        @Query(API_KEY)     apiKey      : String,
        @Query(HASH)        signMD5     : String
    ): Response<CharactersResponse>


}