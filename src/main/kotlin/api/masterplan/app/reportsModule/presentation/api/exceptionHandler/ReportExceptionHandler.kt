package api.masterplan.app.reportsModule.presentation.api.exceptionHandler

import api.masterplan.app.reportsModule.presentation.dto.response.ReportErrorResponse
import api.masterplan.app.reportsModule.presentation.mapper.ReportExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [ReportControllerExceptionHandler::class])
class ReportExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ReportErrorResponse> {
        val status = ReportExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = ReportErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }

}