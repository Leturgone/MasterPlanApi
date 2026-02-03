package api.masterplan.app.authModule.domain.model.value

@JvmInline
value class UserLogin(val value: String){
    companion object{
        fun create(login: String): UserLogin {
            require(login.isNotBlank()) {"Login cant be blank"}
            require(login.length <= 255) { "Login too long" }
            return UserLogin(login.lowercase())
        }
    }
}
