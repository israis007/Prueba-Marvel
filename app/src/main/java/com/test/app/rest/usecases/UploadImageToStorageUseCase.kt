package com.test.app.rest.usecases

import android.net.Uri
import com.test.app.rest.repositories.StorageRepository
import com.test.app.rest.state.Resource
import javax.inject.Inject

class UploadImageToStorageUseCase @Inject constructor(
    val storageRepository: StorageRepository
) {

    suspend operator fun invoke(uri: Uri?, response: (name: Resource<String>) -> Unit) =
        storageRepository.uploadImage(uri){
            response(it)
        }

}