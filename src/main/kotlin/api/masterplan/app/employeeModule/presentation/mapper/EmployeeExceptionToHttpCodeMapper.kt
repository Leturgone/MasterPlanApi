package api.masterplan.app.employeeModule.presentation.mapper

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import org.springframework.http.HttpStatus

object EmployeeExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus{
        return when (ex) {
            is EmployeeException.EmployeeAlreadyExists -> HttpStatus.CONFLICT
            is EmployeeException.EmployeeNotExist -> HttpStatus.NOT_FOUND
            is EmployeeException.EmployeeNotExistWithUserId -> HttpStatus.NOT_FOUND
            is EmployeeException.FailedToCreateEmployee -> HttpStatus.INTERNAL_SERVER_ERROR
            is EmployeeException.FailedToGetDirectorDetailsForEmployee -> HttpStatus.NOT_FOUND
            is EmployeeException.FailedToUpdateEmployee -> HttpStatus.INTERNAL_SERVER_ERROR
            is EmployeeException.InvalidEmployeeName -> HttpStatus.BAD_REQUEST
            is EmployeeException.InvalidEmployeePatronymic -> HttpStatus.BAD_REQUEST
            is EmployeeException.InvalidEmployeeSurname -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}