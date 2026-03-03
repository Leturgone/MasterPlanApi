package api.masterplan.app.plansModule.presentation.api.exceptionHandler

import api.masterplan.app.plansModule.presentation.dto.response.PlanErrorResponse
import api.masterplan.app.plansModule.presentation.mapper.PlanExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [PlanControllerExceptionHandler::class])
class PlanExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<PlanErrorResponse> {
        val status = PlanExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = PlanErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}