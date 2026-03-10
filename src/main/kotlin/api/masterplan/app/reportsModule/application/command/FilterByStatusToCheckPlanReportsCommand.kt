package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportStatus

data class FilterByStatusToCheckPlanReportsCommand(
    val directorId: ReportEmployeeId,
    val status: TaskReportStatus
)