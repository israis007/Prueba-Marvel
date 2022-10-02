package com.test.app.rest.usecases

import com.test.app.rest.repositories.MDBRepository
import com.test.app.rest.responses.PopularPersonsResponse
import com.test.app.rest.state.Resource
import javax.inject.Inject

class GetPopularPersonsCaseUse @Inject  constructor(
    private val repository: MDBRepository
) {
    suspend operator fun invoke(language: String, page: Int, response: (popularPersonsResponse: Resource<PopularPersonsResponse>) -> Unit) =
        repository.getPopularPerson(language = language, page = page){
            response(it)
        }
}