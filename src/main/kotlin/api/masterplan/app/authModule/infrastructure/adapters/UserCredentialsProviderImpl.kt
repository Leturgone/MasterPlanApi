package api.masterplan.app.authModule.infrastructure.adapters

import api.masterplan.app.authModule.application.dto.AuthUserModel
import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.application.ports.UserCredentialsProvider
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import api.masterplan.app.userManagementModule.UserManageModuleService
import org.springframework.stereotype.Component

@Component
class UserCredentialsProviderImpl(
    private val userManageModuleService: UserManageModuleService
): UserCredentialsProvider {

    override fun getUserDetailsByUsername(username: AuthUserLogin): AuthUserModel {
        val userEntity = userManageModuleService.getUserDetailsByUsername(username.value).getOrElse {
            throw AuthInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        val userModel = AuthInnerModuleSuccessMapper.toAuthUserModel(userEntity)
        return userModel
    }

    override fun validateCredentials(login: AuthUserLogin, password: AuthUserPassword): UserAuthCredentials {
        val userCredentials = userManageModuleService.validateCredentials(login.value,password.value).getOrElse {
            throw AuthInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        val userAuthCredentials = AuthInnerModuleSuccessMapper.toUserAuthCredentials(userCredentials)
        return userAuthCredentials
    }
}