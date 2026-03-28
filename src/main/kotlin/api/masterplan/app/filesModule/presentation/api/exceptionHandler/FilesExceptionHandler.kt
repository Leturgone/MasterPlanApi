package api.masterplan.app.filesModule.presentation.api.exceptionHandler

import api.masterplan.app.filesModule.presentation.dto.response.FilesErrorResponse
import api.masterplan.app.filesModule.presentation.mapper.FilesExceptionToHttpCodeMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice(annotations = [FilesControllerExceptionHandler::class])
class FilesExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleEmployeeException(e: Exception): ResponseEntity<FilesErrorResponse> {
        val status = FilesExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = FilesErrorResponse(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }
}