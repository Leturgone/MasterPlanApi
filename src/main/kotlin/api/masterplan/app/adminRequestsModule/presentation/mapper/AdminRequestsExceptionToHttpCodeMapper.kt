package api.masterplan.app.adminRequestsModule.presentation.mapper

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException
import org.springframework.http.HttpStatus

object AdminRequestsExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus{
        return when (ex) {
            is AdminRequestException.AdminAnswerNotExistForRequest -> HttpStatus.NOT_FOUND
            is AdminRequestException.AdminRequestNotExist -> HttpStatus.NOT_FOUND
            is AdminRequestException.FailedToChangeAdminRequestStatus -> HttpStatus.INTERNAL_SERVER_ERROR
            is AdminRequestException.FailedToCreateAdminAnswer -> HttpStatus.INTERNAL_SERVER_ERROR
            is AdminRequestException.FailedToCreateAdminRequest -> HttpStatus.INTERNAL_SERVER_ERROR
            is AdminRequestException.InvalidAdminAnswerDesc -> HttpStatus.BAD_REQUEST
            is AdminRequestException.InvalidAdminAnswerTitle -> HttpStatus.BAD_REQUEST
            is AdminRequestException.InvalidAdminRequestDesc -> HttpStatus.BAD_REQUEST
            is AdminRequestException.InvalidAdminRequestStatus -> HttpStatus.BAD_REQUEST
            is AdminRequestException.InvalidAdminRequestTitle -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}