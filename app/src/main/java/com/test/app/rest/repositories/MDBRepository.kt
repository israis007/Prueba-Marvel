package com.test.app.rest.repositories

import com.test.app.rest.providers.ProvidesAPI
import com.test.app.rest.providers.RETROFIT_MDB
import com.test.app.rest.responses.PopularMoviesResponse
import com.test.app.rest.responses.PopularPersonsResponse
import com.test.app.rest.state.Resource
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class MDBRepository @Inject constructor(
    private val providesAPI: ProvidesAPI,
    @Named(RETROFIT_MDB) private val retrofit: Retrofit
) {

    suspend fun getMovies(
        language: String,
        page: Int,
        response: (popularMoviesResponse: Resource<PopularMoviesResponse>) -> Unit
    ) {
        response(Resource.loading())
        try {
            val temp = providesAPI.mbdAPI(retrofit).getPopularMovies(language = language, page = page)
            if (temp.isSuccessful && temp.code() == 200)
                response(Resource.success(temp.body()!!))
            else
                response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        } catch (e: Exception) {
            response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        }
    }

    suspend fun getPopularPerson(
        language: String,
        page: Int,
        response: (popularPersonsResponse: Resource<PopularPersonsResponse>) -> Unit
    ){
        response(Resource.loading())
        try {
            val temp = providesAPI.mbdAPI(retrofit).getPopularPersons(language = language, page = page)
            if (temp.isSuccessful && temp.code() == 200)
                response(Resource.success(temp.body()!!))
            else
                response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        } catch (e: Exception){
            response(Resource.error("No se pudo comunicar con el servidor de MBD"))
        }
    }

}