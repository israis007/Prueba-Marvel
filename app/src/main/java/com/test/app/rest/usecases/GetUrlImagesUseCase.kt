package com.test.app.rest.usecases

import com.test.app.rest.repositories.StorageRepository
import com.test.app.rest.state.Resource
import javax.inject.Inject

class GetUrlImagesUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(nameFile: String, response: (name: Resource<String>) -> Unit) =
        storageRepository.getUrlDownloadImage(nameFile){
            response(it)
        }

}