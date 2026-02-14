package api.masterplan.app.userManagementModule.infrastructure.security.service

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.PasswordHasherService
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordHasherImpl(
    private val encoder: PasswordEncoder
): PasswordHasherService {

    override fun hash(rawPassword: UserPassword): UserPassword {
        val hashPassword = encoder.encode(rawPassword.value) ?: throw UserManagementException.InvalidUserCredentialsException()
        return UserPassword.validate(hashPassword)
    }

    override fun verify(rawPassword: UserPassword, hash: UserPassword): Boolean {
        return encoder.matches(rawPassword.value,hash.value)
    }

}