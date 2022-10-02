package com.test.app.rest.usecases

import com.test.app.rest.repositories.SessionRepository
import com.test.app.rest.state.Resource
import javax.inject.Inject

class LoginCaseUse @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend operator fun invoke(isLogin: (login: Resource<Boolean>) -> Unit) =
        sessionRepository.login {
            isLogin(it)
        }

}