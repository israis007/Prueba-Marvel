package com.test.app.rest.providers

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class SessionProvider @Inject constructor(){

    fun getAuth() =
        Firebase.auth

}