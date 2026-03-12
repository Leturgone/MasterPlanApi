package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

data class FilterByStatusCreatedPlanReportsCommand(
    val employeeId: ReportEmployeeId,
    val status: ReportStatus
)