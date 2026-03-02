package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.plansModule.PlanModuleErrorDto

internal object EmployeeInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): EmployeeException{
        return when(ex){
            is PlanModuleErrorDto.InternalServerError -> EmployeeException.InternalServerError("Internal plan module error")
            else -> EmployeeException.InternalServerError("Internal plan module error")
        }
    }
}