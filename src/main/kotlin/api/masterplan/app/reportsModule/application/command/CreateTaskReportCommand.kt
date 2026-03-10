package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.value.*

data class CreateTaskReportCommand(
    val id: TaskReportId? = null,
    val title: TaskReportTitle,
    val description: TaskReportDescription? = null,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val document: ReportFile
)