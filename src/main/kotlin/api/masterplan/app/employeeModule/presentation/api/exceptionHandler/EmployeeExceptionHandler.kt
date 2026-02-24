package api.masterplan.app.employeeModule.presentation.api.exceptionHandler

import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeErrorResponse
import api.masterplan.app.employeeModule.presentation.mapper.EmployeeExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [EmployeeControllerExceptionHandler::class])
class EmployeeExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleEmployeeException(e: Exception, ): ResponseEntity<EmployeeErrorResponse> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = EmployeeErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}