package com.test.app.rest.usecases

import com.test.app.rest.repositories.MDBRepository
import com.test.app.rest.responses.PopularMoviesResponse
import com.test.app.rest.state.Resource
import javax.inject.Inject

class GetPopularMoviesCaseUse @Inject constructor(
    private val repository: MDBRepository
) {
    suspend operator fun invoke(language: String, page: Int, response: (popularMoviesResponse: Resource<PopularMoviesResponse>) -> Unit) =
        repository.getMovies(language = language, page = page){
            response(it)
        }
}