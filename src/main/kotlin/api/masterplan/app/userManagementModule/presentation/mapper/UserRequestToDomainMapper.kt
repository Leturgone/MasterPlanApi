package api.masterplan.app.userManagementModule.presentation.mapper

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import java.util.UUID

object UserRequestToDomainMapper {

    fun idToDomain(id: UUID) = UserId(id)

    fun loginToDomain(login: String) = UserLogin.validate(login)

    fun passwordToDomain(password: String) = UserPassword.validate(password)

}