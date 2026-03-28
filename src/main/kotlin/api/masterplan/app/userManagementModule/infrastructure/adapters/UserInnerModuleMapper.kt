package api.masterplan.app.userManagementModule.infrastructure.adapters

import api.masterplan.app.apiContracts.employee.EmployeeDataDto
import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.domain.models.value.UserId

internal object UserInnerModuleMapper {
    fun toDto(userId: UserId, employeeInfo: EmployeeInfo): EmployeeDataDto {
        return EmployeeDataDto(
            name = employeeInfo.name,
            surname = employeeInfo.surname,
            patronymic = employeeInfo.patronymic,
            directorId = employeeInfo.directorId,
            userId = userId.value
        )
    }
}