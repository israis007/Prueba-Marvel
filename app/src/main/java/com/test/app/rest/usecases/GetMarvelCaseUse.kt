package com.test.app.rest.usecases

import com.test.app.rest.apimarvel.MarvelRepository
import javax.inject.Inject


class GetMarvelCaseUse @Inject constructor(
    private val repository: MarvelRepository
) {

    fun getCharacters() = repository.getResponse()

    operator fun invoke(limit: Int) = repository.getCharacters(limit)

}