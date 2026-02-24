package api.masterplan.app.authModule.domain.model.value

import api.masterplan.app.authModule.domain.exception.AuthException

@JvmInline
value class AuthUserLogin(val value: String){
    companion object{
        fun validate(login: String): AuthUserLogin {
            try {
                require(login.isNotBlank()) {"Login cant be blank"}
                require(login.length <= 255) { "Login too long" }
            }catch (e: IllegalArgumentException){
                throw AuthException.InvalidLoginOrPassword(e.message ?: "")
            }

            return AuthUserLogin(login.uppercase())
        }
    }
}
