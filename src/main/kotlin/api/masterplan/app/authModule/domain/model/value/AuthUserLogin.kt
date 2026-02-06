package api.masterplan.app.authModule.domain.model.value

@JvmInline
value class AuthUserLogin(val value: String){
    companion object{
        fun validate(login: String): AuthUserLogin {
            require(login.isNotBlank()) {"Login cant be blank"}
            require(login.length <= 255) { "Login too long" }
            return AuthUserLogin(login.lowercase())
        }
    }
}
