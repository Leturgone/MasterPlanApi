package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.employeeModule.EmployeeModuleErrorDto
import api.masterplan.app.reportsModule.domain.exceptions.ReportException

internal object ReportInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): ReportException{
        return when(ex){
            is EmployeeModuleErrorDto.InternalServerError -> ReportException.InternalServerError(ex.message)
            else -> ReportException.InternalServerError()
        }
    }
}