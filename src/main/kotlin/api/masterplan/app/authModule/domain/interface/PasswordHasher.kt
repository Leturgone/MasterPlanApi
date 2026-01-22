package api.masterplan.app.authModule.domain.`interface`

import api.masterplan.app.authModule.domain.model.value.UserPassword

interface PasswordHasher {

    fun hash(password: UserPassword): UserPassword

    fun  verify(password: UserPassword, hash: UserPassword)
}