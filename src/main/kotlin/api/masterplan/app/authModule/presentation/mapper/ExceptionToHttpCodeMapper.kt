package api.masterplan.app.authModule.presentation.mapper

import api.masterplan.app.authModule.domain.exception.AuthException
import org.springframework.http.HttpStatus

object ExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus {
        return when (ex) {
            is AuthException.TokenGenerationException -> HttpStatus.BAD_REQUEST
            is AuthException.UserNotExistsWithLogin -> HttpStatus.UNAUTHORIZED
            is AuthException.InvalidCredentials -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}