package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.application.ports.EmployeeFilesCreator
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import org.springframework.stereotype.Component

@Component
class EmployeeFilesCreatorImpl(): EmployeeFilesCreator {
    override fun createDirEmployeesExcelTable(employeeData: List<EmployeeWithMetricsDetails>): FileModel {
        print("createDirEmployeesExcelTable")
        TODO("Not yet implemented")
    }
}