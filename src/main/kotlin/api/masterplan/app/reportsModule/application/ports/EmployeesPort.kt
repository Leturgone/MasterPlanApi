package api.masterplan.app.reportsModule.application.ports

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

interface EmployeesPort {

    fun getSubordinates(directorId: ReportEmployeeId): Set<ReportEmployeeId>

}