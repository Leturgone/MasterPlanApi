package api.masterplan.app.userManagementModule.application.ports

import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.domain.models.value.UserId

interface EmployeeCreationPort {
    fun createEmployee(userId: UserId, employeeInfo: EmployeeInfo)
}