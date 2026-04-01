package api.masterplan.app.reportsModule.application.ports

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

interface ReportEmployeesPort {

    fun getSubordinates(directorId: ReportEmployeeId): Set<ReportEmployeeId>

    fun getDirectorId(employeeId: ReportEmployeeId): ReportEmployeeId?

}