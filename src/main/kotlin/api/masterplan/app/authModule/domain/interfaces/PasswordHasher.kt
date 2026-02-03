package api.masterplan.app.authModule.domain.interfaces

import api.masterplan.app.authModule.domain.model.value.UserPassword

interface PasswordHasher {

    fun hash(rawPassword: UserPassword): UserPassword

    fun  verify(rawPassword: UserPassword, hash: UserPassword): Boolean
}