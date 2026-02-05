package api.masterplan.app.authModule.application.service

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.UserRole

interface TokenGeneratorService {

    fun generateToken(authUserId: AuthUserId, userRoles: Set<UserRole>): Result<JwtToken>
}