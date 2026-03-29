package api.masterplan.app.adminRequestsModule.presentation.api.exceptionHandler

import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminRequestsErrorResponse
import api.masterplan.app.adminRequestsModule.presentation.mapper.AdminRequestsExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [AdminRequestsControllerExceptionHandler::class])
class AdminRequestsExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleEmployeeException(e: Exception): ResponseEntity<AdminRequestsErrorResponse> {
        val status = AdminRequestsExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = AdminRequestsErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}