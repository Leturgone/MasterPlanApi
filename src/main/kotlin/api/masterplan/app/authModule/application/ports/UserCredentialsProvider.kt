package api.masterplan.app.authModule.application.ports

import api.masterplan.app.authModule.application.dto.AuthUserModel
import api.masterplan.app.authModule.application.dto.UserAuthCredentials
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword

interface UserCredentialsProvider {

    fun getUserDetailsByUsername(username: AuthUserLogin): AuthUserModel

    fun validateCredentials(login: AuthUserLogin,password: AuthUserPassword): UserAuthCredentials

}