package com.test.app.rest.repositories

import com.test.app.rest.providers.ProvidesFirestore
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    val providesFirestore: ProvidesFirestore
) {

    suspend fun registerUbication(){}

}