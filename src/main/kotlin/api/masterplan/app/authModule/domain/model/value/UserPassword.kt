package api.masterplan.app.authModule.domain.model.value

@JvmInline
value class UserPassword(val password: String){
    companion object {
        fun create(password: String): UserPassword{
            validateStrength(password)
            return UserPassword(password)
        }

        private fun validateStrength(password: String){
            require(password.isNotBlank()) {"Password cant be blank"}
            require(password.length >= 8) { "Password too short" }
            require(password.length <= 255) { "Password too long" }
        }
    }
}
