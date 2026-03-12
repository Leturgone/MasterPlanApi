package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus

data class ChangePlanReportStatusCommand(
    val reportId: PlanReportId,
    val status: ReportStatus
)