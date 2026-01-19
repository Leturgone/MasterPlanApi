package api.masterplan.app.authModule.application.services

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserRole

interface TokenGeneratorService {

    fun generateToken(userId: UserId,userRoles: Set<UserRole>): Result<JwtToken>
}