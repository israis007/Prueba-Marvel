package com.test.app.rest.providers

import com.google.firebase.firestore.FirebaseFirestore
import com.test.app.ui.tools.COLLECTION_STORAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProvidesFirestore {

    @Provides
    @Singleton
    fun getStorageImages() =
        FirebaseFirestore.getInstance().collection(COLLECTION_STORAGE)

}