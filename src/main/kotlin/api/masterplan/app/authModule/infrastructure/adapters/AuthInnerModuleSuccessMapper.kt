package api.masterplan.app.authModule.infrastructure.adapters

import api.masterplan.app.authModule.application.dto.AuthUserModel
import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import api.masterplan.app.authModule.domain.model.value.AuthUserRole
import api.masterplan.app.userManagementModule.UserCredentialsDto

internal object AuthInnerModuleSuccessMapper {

    fun toAuthUserModel(user: UserCredentialsDto ): AuthUserModel {
        return AuthUserModel(
            login = AuthUserLogin.validate(user.login),
            password = AuthUserPassword.validate(user.password),
            roles = user.roles.map {AuthUserRole.valueOf(it)}.toSet()
        )
    }

    fun toUserAuthCredentials(user: UserCredentialsDto): UserAuthCredentials {
        return UserAuthCredentials(
            authUserName = AuthUserLogin(user.login),
            roles = user.roles.map {AuthUserRole.valueOf(it)}.toSet()
        )
    }

}