package api.masterplan.app.authModule.infrastructure.security.hasher

import api.masterplan.app.authModule.domain.interfaces.PasswordHasher
import api.masterplan.app.authModule.domain.model.value.UserPassword
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanPasswordHashException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordHasherImpl(
    private val encoder: PasswordEncoder
): PasswordHasher {

    override fun hash(rawPassword: UserPassword): UserPassword {
        val hashPassword = encoder.encode(rawPassword.value) ?: throw MasterPlanPasswordHashException.EmptyPassword()
        return UserPassword.create(hashPassword)
    }

    override fun verify(rawPassword: UserPassword, hash: UserPassword): Boolean {
        return encoder.matches(rawPassword.value,hash.value)
    }

}