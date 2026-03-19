package api.masterplan.app.reportsModule.domain.models.value

sealed class ReportReferenceId {
    data class ForTask(val taskId: ReportTaskId) : ReportReferenceId()
    data class ForPlan(val planId: ReportPlanId) : ReportReferenceId()
}