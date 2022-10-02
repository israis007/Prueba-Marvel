package com.test.app.rest.repositories

import android.net.Uri
import com.test.app.rest.providers.ProvidesStorage
import com.test.app.rest.state.Resource
import com.test.app.ui.tools.convertToByteArray
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storage: ProvidesStorage
) {

    suspend fun uploadImage(uri: Uri?, response: (name: Resource<String>) -> Unit){
        response(Resource.loading())
        try {
            val bytes = uri?.convertToByteArray()
            val name = uri?.toString()?.substringAfterLast('/')
            if (bytes != null && !name.isNullOrEmpty()){
                storage.getStorageImages().child(name)
                    .putBytes(bytes)
                    .addOnCompleteListener { complete ->
                        if (complete.isSuccessful)
                            response(Resource.success(name))
                        else
                            response(Resource.error("Error al subir la imagen, intente más tarde"))
                    }.addOnFailureListener { fail ->
                        response(Resource.error(fail.message ?: "Error al subir la imagen, intente más tarde"))
                    }
            }
        } catch (e: Exception){
            response(Resource.error("No se pudo subir la imagen, intente más tarde..."))
        }
    }

    suspend fun getUrlDownloadImage(nameFile: String, response: (name: Resource<String>) -> Unit){
        response(Resource.loading())
        try {
            storage.getStorageImages().child(nameFile).downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    response(Resource.success(task.result.toString()))
                else
                    response(Resource.error("Error al obtener la url de la imagen"))
            }.addOnFailureListener { fail ->
                response(Resource.error(fail.message ?: "Error al obtener la url de la imagen"))
            }
        } catch (e: Exception){
            response(Resource.error("Error al obtener la url de la imagen"))
        }
    }

    suspend fun getListImages(response: (list: Resource<List<String>>) -> Unit){
        response(Resource.loading())
        try {
            storage.getStorageImages().listAll().addOnCompleteListener { task ->
                val array = ArrayList<String>()
                if (task.isSuccessful) {
                    task.result.items.forEach {
                        array.add(it.name)
                    }
                    response(Resource.success(array.toList()))
                } else
                    response(Resource.error("Error al obtener la lista de imágenes"))
            }.addOnFailureListener { fail ->
                response(Resource.error(fail.message ?: "Error al obtener la lista de imágenes"))
            }
        } catch (e: Exception){
            response(Resource.error("Error al obtener la lista de imágenes"))
        }
    }

}