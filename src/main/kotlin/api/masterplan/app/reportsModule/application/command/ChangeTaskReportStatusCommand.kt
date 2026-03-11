package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportStatus

data class ChangeTaskReportStatusCommand(
    val reportId: TaskReportId,
    val status: TaskReportStatus
)