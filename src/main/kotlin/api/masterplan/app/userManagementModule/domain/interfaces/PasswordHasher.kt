package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.domain.models.value.UserPassword


interface PasswordHasher {

    fun hash(rawPassword: UserPassword): UserPassword

    fun  verify(rawPassword: UserPassword, hash: UserPassword): Boolean
}