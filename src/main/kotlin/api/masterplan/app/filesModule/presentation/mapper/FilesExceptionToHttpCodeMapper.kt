package api.masterplan.app.filesModule.presentation.mapper

import api.masterplan.app.filesModule.domain.exceptions.FilesException
import org.springframework.http.HttpStatus

object FilesExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus {
        return when (ex) {
            is FilesException.FileNotExist -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}