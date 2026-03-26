package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.apiContracts.files.FilesModuleErrorDto
import api.masterplan.app.apiContracts.plans.PlanModuleErrorDto

internal object EmployeeInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): EmployeeException{
        return when(ex){
            is PlanModuleErrorDto.InternalServerError -> EmployeeException.InternalServerError("Internal plan module error")
            is FilesModuleErrorDto.InternalServerError -> EmployeeException.InternalServerError("Internal files module error")
            else -> EmployeeException.InternalServerError("Internal error")
        }
    }
}