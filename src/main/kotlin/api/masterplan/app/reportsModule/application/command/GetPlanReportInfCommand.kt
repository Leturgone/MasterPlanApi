package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.PlanReportId

data class GetPlanReportInfCommand(
    val planReportId: PlanReportId
)