package api.masterplan.app.authModule.domain.model.value

@JvmInline
value class AuthUserPassword(val value: String){
    companion object {
        fun validate(password: String): AuthUserPassword{
            require(password.isNotBlank()) {"Password cant be blank"}
            require(password.length >= 8) { "Password too short" }
            require(password.length <= 255) { "Password too long" }
            return AuthUserPassword(password)
        }

    }
}
