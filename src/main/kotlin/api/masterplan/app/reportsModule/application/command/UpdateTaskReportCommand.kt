package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId

data class UpdateTaskReportCommand (
    val reportId: TaskReportId,
    val updatedReport: TaskReport,
    val document: ReportFile
)