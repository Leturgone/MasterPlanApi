package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus

data class FilterByStatusCreatedTaskReportsCommand(
    val employeeId: ReportEmployeeId,
    val status: ReportStatus
)