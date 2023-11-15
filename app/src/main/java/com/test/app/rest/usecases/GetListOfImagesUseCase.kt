package com.test.app.rest.usecases

import com.test.app.rest.repositories.StorageRepository
import com.test.app.rest.state.Resource
import javax.inject.Inject

class GetListOfImagesUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(response: (list: Resource<List<String>>) -> Unit) =
        storageRepository.getListImages {
            response(it)
        }

}