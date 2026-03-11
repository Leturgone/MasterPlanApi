package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.value.*

data class CreatePlanReportCommand(
    val id: PlanReportId? = null,
    val title: PlanReportTitle,
    val description: PlanReportDescription? = null,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val document: ReportFile,
)