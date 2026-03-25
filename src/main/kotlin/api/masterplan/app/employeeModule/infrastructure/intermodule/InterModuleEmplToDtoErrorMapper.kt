package api.masterplan.app.employeeModule.infrastructure.intermodule

import api.masterplan.app.employeeModule.EmployeeModuleErrorDto
import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException

internal object InterModuleEmplToDtoErrorMapper {
    fun toDto(exception: Throwable): EmployeeModuleErrorDto{
        return when (exception) {
            is EmployeeException.InvalidEmployeeName -> EmployeeModuleErrorDto.InvalidEmployeeName(exception.message)
            is EmployeeException.InvalidEmployeeSurname -> EmployeeModuleErrorDto.InvalidEmployeeSurname(exception.message)
            is EmployeeException.InvalidEmployeePatronymic -> EmployeeModuleErrorDto.InvalidEmployeePatronymic(exception.message)
            is EmployeeException.FailedToCreateEmployee -> EmployeeModuleErrorDto.FailedToCreateEmployee(exception.name.value,exception.surname.value,exception.patronymic?.value)
            is EmployeeException.EmployeeAlreadyExists -> EmployeeModuleErrorDto.EmployeeAlreadyExists(exception.name.value,exception.surname.value,exception.patronymic?.value)
            is EmployeeException.EmployeeNotExist -> EmployeeModuleErrorDto.EmployeeNotExist(exception.id.value)
            else -> EmployeeModuleErrorDto.InternalServerError()
        }
    }
}