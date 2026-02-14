package api.masterplan.app.authModule.domain.interfaces

import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword

interface AuthService {

    fun authenticate(login: AuthUserLogin, password: AuthUserPassword): UserAuthCredentials
}