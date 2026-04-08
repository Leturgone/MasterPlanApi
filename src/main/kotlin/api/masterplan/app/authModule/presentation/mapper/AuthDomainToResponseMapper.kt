package api.masterplan.app.authModule.presentation.mapper

import api.masterplan.app.authModule.domain.dto.JwtToken
import api.masterplan.app.authModule.presentation.dto.LoginResponse

object AuthDomainToResponseMapper {
    fun toLoginResponse(token: JwtToken): LoginResponse {
        return LoginResponse(
            token = token.token,
            roles = token.roles.map { it.name }.toList(),
            id = token.authUserId.value
        )
    }
}