package api.masterplan.app.authModule.domain.interfaces

import api.masterplan.app.authModule.domain.dto.JwtToken
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserRole

interface TokenGeneratorService {

    fun generateToken(authUserName: AuthUserLogin, authUserRoles: Set<AuthUserRole>): JwtToken
}