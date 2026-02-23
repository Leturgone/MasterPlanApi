package api.masterplan.app.userManagementModule.presentation.api.exceptionHandler

import api.masterplan.app.userManagementModule.presentation.dto.responce.UserErrorResponse
import api.masterplan.app.userManagementModule.presentation.mapper.UserExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [UserControllerExceptionHandler::class])
class UserExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleUserException(e: Exception, ): ResponseEntity<UserErrorResponse> {
        val status = UserExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = UserErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}