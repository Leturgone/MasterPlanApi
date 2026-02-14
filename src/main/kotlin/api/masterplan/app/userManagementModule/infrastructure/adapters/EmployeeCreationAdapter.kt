package api.masterplan.app.userManagementModule.infrastructure.adapters

import api.masterplan.app.userManagementModule.application.dto.EmployeeInfo
import api.masterplan.app.userManagementModule.application.ports.EmployeeCreationPort
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import org.springframework.stereotype.Service

@Service
class EmployeeCreationAdapter: EmployeeCreationPort {
    override fun createEmployee(
        userId: UserId,
        employeeInfo: EmployeeInfo
    ) {
        print(employeeInfo.name)
        TODO("Реализовать адаптер")
    }
}