package api.masterplan.app.apiContracts.userManagement

import java.util.UUID

data class UserCredentialsDto(
    val userId: UUID,
    val password: String,
    val login: String,
    val roles: Set<String>
)
