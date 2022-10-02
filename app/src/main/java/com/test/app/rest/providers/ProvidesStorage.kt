package com.test.app.rest.providers

import com.google.firebase.storage.FirebaseStorage
import com.test.app.ui.tools.DIRECTORY_IMAGES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProvidesStorage @Inject constructor(){

    @Provides
    @Singleton
    fun getStorageImages() =
        FirebaseStorage.getInstance().reference.child(DIRECTORY_IMAGES)

}