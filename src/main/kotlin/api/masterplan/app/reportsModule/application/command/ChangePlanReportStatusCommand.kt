package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.PlanReportStatus

data class ChangePlanReportStatusCommand(
    val reportId: PlanReportId,
    val status: PlanReportStatus
)