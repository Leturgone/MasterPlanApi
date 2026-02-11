package api.masterplan.app.userManagementModule.domain.models.value

@JvmInline
value class UserLogin(val value: String){
    companion object{
        fun validate(login: String): UserLogin {
            require(login.isNotBlank()) {"Login cant be blank"}
            require(login.length <= 255) { "Login too long" }
            return UserLogin(login.lowercase())
        }
    }
}
