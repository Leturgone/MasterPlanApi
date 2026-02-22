package api.masterplan.app.userManagementModule.infrastructure.adapters

import api.masterplan.app.employeeModule.EmployeeModuleService
import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.ports.EmployeeCreationPort
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class EmployeeCreationAdapter(
    private val employeeModuleService: EmployeeModuleService
): EmployeeCreationPort {

    override fun createEmployee(userId: UserId, employeeInfo: EmployeeInfo) {
        val employeeData = UserInnerModuleMapper.toDto(userId, employeeInfo)
        employeeModuleService.createEmployee(employeeData).getOrElse {
            throw UserInnerModuleErrorMapper.exceptionToModuleException(it)
        }

    }
}