package com.test.app.rest.repositories

import com.test.app.BuildConfig
import com.test.app.rest.providers.SessionProvider
import com.test.app.rest.state.Resource
import javax.inject.Inject

class SessionRepository @Inject constructor(
    val authProvider: SessionProvider
) {

    suspend fun login(isLogin: (login: Resource<Boolean>) -> Unit){
        isLogin(Resource.loading())
        authProvider.getAuth().signInWithEmailAndPassword(BuildConfig.USER_FB, BuildConfig.PASS_FB)
            .addOnCompleteListener { task ->
                isLogin(Resource.success(task.isSuccessful))
            }.addOnFailureListener { fail ->
                isLogin(Resource.error(fail.message ?: "Error al iniciar sesión"))
            }
    }

}