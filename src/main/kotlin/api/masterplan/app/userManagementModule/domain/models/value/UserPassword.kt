package api.masterplan.app.userManagementModule.domain.models.value

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException

@JvmInline
value class UserPassword(val value: String){
    companion object {
        fun validate(password: String): UserPassword{
            try {
                require(password.isNotBlank()) {"Password cant be blank"}
                require(password.length >= 8) { "Password too short" }
                require(password.length <= 255) { "Password too long" }
            }catch (e: IllegalArgumentException){
                throw UserManagementException.InvalidUserCredentialsException(e.message)
            }
            return UserPassword(password)
        }
    }
}
