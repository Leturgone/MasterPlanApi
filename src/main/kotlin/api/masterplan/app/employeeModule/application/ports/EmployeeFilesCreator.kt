package api.masterplan.app.employeeModule.application.ports

import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails

interface EmployeeFilesCreator {
    fun createDirEmployeesExcelTable(employeeData: List<EmployeeWithMetricsDetails>): FileModel
}