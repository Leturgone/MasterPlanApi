package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import org.springframework.http.HttpStatus

object ReportExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Exception): HttpStatus{
        return when (ex) {
            is ReportException.FailedToDeleteReport -> HttpStatus.INTERNAL_SERVER_ERROR
            is ReportException.FailedToSaveReport -> HttpStatus.INTERNAL_SERVER_ERROR
            is ReportException.FailedToUpdateReport -> HttpStatus.INTERNAL_SERVER_ERROR
            is ReportException.FailedToUpdateReportStatus -> HttpStatus.INTERNAL_SERVER_ERROR
            is ReportException.InternalServerError -> HttpStatus.INTERNAL_SERVER_ERROR
            is ReportException.InvalidReferenceId -> HttpStatus.BAD_REQUEST
            is ReportException.InvalidReportDescription -> HttpStatus.BAD_REQUEST
            is ReportException.InvalidReportStatus -> HttpStatus.BAD_REQUEST
            is ReportException.InvalidReportTitle -> HttpStatus.BAD_REQUEST
            is ReportException.ReportAlreadyExist -> HttpStatus.CONFLICT
            is ReportException.ReportNotExist -> HttpStatus.NOT_FOUND
            is ReportException.InvalidReportType -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}