package api.masterplan.app.employeeModule.infrastructure.intermodule

import api.masterplan.app.employeeModule.EmployeeDataDto
import api.masterplan.app.employeeModule.domain.model.entity.Employee

internal object InterModuleEmplToDtoSuccessMapper {
    fun toDto(employee: Employee): EmployeeDataDto{
        return EmployeeDataDto(
            name = employee.name.value,
            surname = employee.surname.value,
            patronymic = employee.patronymic?.value,
            directorId = employee.directorId?.value,
            userId = employee.userId.value
        )
    }
}