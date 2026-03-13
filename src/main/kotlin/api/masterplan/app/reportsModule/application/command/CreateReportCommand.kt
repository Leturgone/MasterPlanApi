package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.value.*

data class CreateReportCommand(
    val id: ReportId? = null,
    val title: ReportTitle,
    val description: ReportDescription? = null,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val document: ReportFile,
    val reportType: ReportType
)