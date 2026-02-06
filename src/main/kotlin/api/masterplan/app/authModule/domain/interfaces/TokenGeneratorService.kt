package api.masterplan.app.authModule.domain.interfaces

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserRole

interface TokenGeneratorService {

    fun generateToken(authUserId: AuthUserId, authUserRoles: Set<AuthUserRole>): Result<JwtToken>
}