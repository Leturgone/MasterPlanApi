package api.masterplan.app.authModule.presentation.mapper

import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword

object RequestToDomainMapper {

    fun toDomainLogin(requestLogin: String) = AuthUserLogin.validate(requestLogin)

    fun toDomainPassword(requestPassword: String) = AuthUserPassword.validate(requestPassword)
}