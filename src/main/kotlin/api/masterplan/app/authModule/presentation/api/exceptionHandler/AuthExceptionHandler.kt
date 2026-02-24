package api.masterplan.app.authModule.presentation.api.exceptionHandler

import api.masterplan.app.authModule.presentation.dto.AuthErrorResponse
import api.masterplan.app.authModule.presentation.mapper.AuthExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [AuthControllerExceptionHandler::class])
class AuthExceptionHandler {


    @ExceptionHandler(Exception::class)
    fun handleAuthException(e: Exception): ResponseEntity<AuthErrorResponse> {
        val status = AuthExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = AuthErrorResponse(
            status = status.value(),e.message, LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}