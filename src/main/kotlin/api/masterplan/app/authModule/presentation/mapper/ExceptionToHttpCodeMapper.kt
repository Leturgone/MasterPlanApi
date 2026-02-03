package api.masterplan.app.authModule.presentation.mapper

import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanAuthException
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanPasswordHashException
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import org.springframework.http.HttpStatus

object ExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus {
        return when (ex) {
            is MasterPlanPasswordHashException.EmptyPassword  -> HttpStatus.BAD_REQUEST
            is MasterPlanTokenException.TokenGenerationException -> HttpStatus.BAD_REQUEST
            is MasterPlanAuthException.UserNotExistsWithLogin -> HttpStatus.UNAUTHORIZED
            is MasterPlanAuthException.InvalidCredentials -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}