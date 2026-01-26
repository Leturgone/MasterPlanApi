package api.masterplan.app.authModule.application.service

import api.masterplan.app.authModule.application.dto.UserCredentials
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.domain.model.value.UserPassword

interface AuthService {

    suspend fun authenticate(login: UserLogin, password: UserPassword): Result<UserCredentials>
}