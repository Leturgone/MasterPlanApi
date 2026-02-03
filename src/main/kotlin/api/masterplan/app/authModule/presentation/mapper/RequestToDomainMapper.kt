package api.masterplan.app.authModule.presentation.mapper

import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.domain.model.value.UserPassword

object RequestToDomainMapper {

    fun toDomainLogin(requestLogin: String) = UserLogin.create(requestLogin)

    fun toDomainPassword(requestPassword: String) = UserPassword.create(requestPassword)
}