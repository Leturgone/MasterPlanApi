package api.masterplan.app.userManagementModule.domain.models.value

@JvmInline
value class UserPassword(val value: String){
    companion object {
        fun validate(password: String): UserPassword{

            require(password.isNotBlank()) {"Password cant be blank"}
            require(password.length >= 8) { "Password too short" }
            require(password.length <= 255) { "Password too long" }

            return UserPassword(password)
        }
    }
}
