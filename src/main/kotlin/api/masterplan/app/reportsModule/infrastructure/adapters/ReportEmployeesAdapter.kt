package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.apiContracts.employee.EmployeeModuleService
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import org.springframework.stereotype.Component

@Component
class ReportEmployeesAdapter(
    private val employeeModuleService: EmployeeModuleService
): ReportEmployeesPort {

    override fun getSubordinates(directorId: ReportEmployeeId): Set<ReportEmployeeId> {
        val subordinatesList = employeeModuleService.getSubordinateEmployees(directorId.value).getOrElse {
            throw ReportInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        val subordinatesModelList = ReportInnerModuleSuccessMapper.toReportEmpIdSet(subordinatesList)
        return subordinatesModelList
    }
}