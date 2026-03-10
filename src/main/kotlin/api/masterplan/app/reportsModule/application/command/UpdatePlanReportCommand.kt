package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.entity.PlanReport
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId

data class UpdatePlanReportCommand(
    val reportId: PlanReportId,
    val updatedReport: PlanReport,
    val document: ReportFile
)