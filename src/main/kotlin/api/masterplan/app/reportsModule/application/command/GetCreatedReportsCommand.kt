package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class GetCreatedReportsCommand(
    val employeeId: ReportEmployeeId,
    val reportType: ReportType
)