package api.masterplan.app.userManagementModule.infrastructure.adapters

import api.masterplan.app.employeeModule.EmployeeModuleErrorDto
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin

internal object UserInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): UserManagementException{
        return when(ex){
            is EmployeeModuleErrorDto.InvalidEmployeeName -> UserManagementException.InvalidUserCredentialsException()
            is EmployeeModuleErrorDto.InvalidEmployeeSurname -> UserManagementException.InvalidUserCredentialsException()
            is EmployeeModuleErrorDto.InvalidEmployeePatronymic -> UserManagementException.InvalidUserCredentialsException()
            is EmployeeModuleErrorDto.FailedToCreateEmployee -> UserManagementException.FailedToCreateUserException(
                UserLogin.validate("${ex.name} ${ex.surname}")
            )
            is EmployeeModuleErrorDto.EmployeeAlreadyExists -> UserManagementException.UserAlreadyExistsException(
                UserLogin.validate("${ex.name} ${ex.surname}")
            )
            is EmployeeModuleErrorDto.InternalServerError -> UserManagementException.InternalServerError()
            else -> UserManagementException.InternalServerError()
        }
    }
}