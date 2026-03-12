package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus

data class ChangeTaskReportStatusCommand(
    val reportId: TaskReportId,
    val status: ReportStatus
)