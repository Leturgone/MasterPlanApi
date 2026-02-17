package api.masterplan.app.userManagementModule.domain.models.value

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException

@JvmInline
value class UserLogin(val value: String){
    companion object{
        fun validate(login: String): UserLogin {
            try {
                require(login.isNotBlank()) {"Login cant be blank"}
                require(login.length <= 255) { "Login too long" }
            }catch (e:IllegalArgumentException){
                throw UserManagementException.InvalidUserCredentialsException(e.message)
            }
            return UserLogin(login.uppercase())
        }
    }
}
