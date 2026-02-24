package api.masterplan.app.authModule.domain.model.value

import api.masterplan.app.authModule.domain.exception.AuthException

@JvmInline
value class AuthUserPassword(val value: String){
    companion object {
        fun validate(password: String): AuthUserPassword{
            try {
                require(password.isNotBlank()) {"Password cant be blank"}
                require(password.length >= 8) { "Password too short" }
                require(password.length <= 255) { "Password too long" }
            }catch (e: IllegalArgumentException){
                throw AuthException.InvalidLoginOrPassword(e.message ?: "")
            }
            return AuthUserPassword(password)
        }

    }
}
